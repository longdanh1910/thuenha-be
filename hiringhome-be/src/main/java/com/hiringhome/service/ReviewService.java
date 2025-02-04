package com.hiringhome.service;

import com.hiringhome.dto.review.CreateReviewRequest;
import com.hiringhome.dto.review.ReviewResponse;
import com.hiringhome.dto.review.UpdateReviewRequest;
import com.hiringhome.entity.Booking;
import com.hiringhome.entity.Review;
import com.hiringhome.entity.User;
import com.hiringhome.entity.enums.BookingStatus;
import com.hiringhome.repository.BookingRepository;
import com.hiringhome.repository.ReviewRepository;
import com.hiringhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ReviewResponse createReview(String username, CreateReviewRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getTenant().getId().equals(user.getId())) {
            throw new RuntimeException("You can only review your own bookings");
        }

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new RuntimeException("You can only review completed bookings");
        }

        if (reviewRepository.existsByBooking(booking)) {
            throw new RuntimeException("You have already reviewed this booking");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);
        updatePropertyRating(booking.getProperty().getId());

        return toReviewResponse(savedReview);
    }

    public ReviewResponse updateReview(String username, Long reviewId, UpdateReviewRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getBooking().getTenant().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update your own reviews");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);
        updatePropertyRating(review.getBooking().getProperty().getId());

        return toReviewResponse(savedReview);
    }

    public void deleteReview(String username, Long reviewId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getBooking().getTenant().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own reviews");
        }

        reviewRepository.delete(review);
        updatePropertyRating(review.getBooking().getProperty().getId());
    }

    public Page<ReviewResponse> getPropertyReviews(Long propertyId, Pageable pageable) {
        return reviewRepository.findByBooking_Property_IdOrderByCreatedAtDesc(propertyId, pageable)
                .map(this::toReviewResponse);
    }

    private void updatePropertyRating(Long propertyId) {
        Double averageRating = reviewRepository.calculateAverageRatingByPropertyId(propertyId);
        if (averageRating != null) {
            reviewRepository.updatePropertyRating(propertyId, averageRating);
        }
    }

    private ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .bookingId(review.getBooking().getId())
                .propertyId(review.getBooking().getProperty().getId())
                .propertyTitle(review.getBooking().getProperty().getTitle())
                .userId(review.getBooking().getTenant().getId())
                .userFullName(review.getBooking().getTenant().getFullName())
                .userAvatarUrl(review.getBooking().getTenant().getAvatarUrl())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
