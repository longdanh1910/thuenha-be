//package com.hiringhome.service;
//
//import com.hiringhome.dto.review.ReviewRequest;
//import com.hiringhome.dto.review.ReviewResponse;
//import com.hiringhome.entity.Property;
//import com.hiringhome.entity.Review;
//import com.hiringhome.entity.User;
//import com.hiringhome.repository.PropertyRepository;
//import com.hiringhome.repository.ReviewRepository;
//import com.hiringhome.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class ReviewService {
//    private final ReviewRepository reviewRepository;
//    private final PropertyRepository propertyRepository;
//    private final UserRepository userRepository;
//    private final NotificationService notificationService;
//
//    @Transactional
//    public ReviewResponse createReview(Long propertyId, ReviewRequest request) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//
//        // Check if user has already reviewed this property
//        if (reviewRepository.existsByPropertyIdAndUserId(propertyId, user.getId())) {
//            throw new RuntimeException("You have already reviewed this property");
//        }
//
//        Review review = new Review();
//        review.setProperty(property);
//        review.setUser(user);
//        review.setRating(request.getRating());
//        review.setComment(request.getComment());
//
//        Review savedReview = reviewRepository.save(review);
//
//        // Update property average rating
//        Double averageRating = reviewRepository.calculateAverageRating(propertyId);
//        property.setAverageRating(new java.math.BigDecimal(averageRating));
//        propertyRepository.save(property);
//
//        // Send notification to property owner
//        notificationService.sendReviewNotification(property.getOwner(), savedReview);
//
//        return mapToResponse(savedReview);
//    }
//
//    public Page<ReviewResponse> getPropertyReviews(Long propertyId, Pageable pageable) {
//        return reviewRepository.findByPropertyId(propertyId, pageable)
//                .map(this::mapToResponse);
//    }
//
//    public void deleteReview(Long reviewId) {
//        Review review = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new RuntimeException("Review not found"));
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (!review.getUser().getUsername().equals(username)) {
//            throw new RuntimeException("You can only delete your own reviews");
//        }
//
//        reviewRepository.delete(review);
//
//        // Update property average rating
//        Double averageRating = reviewRepository.calculateAverageRating(review.getProperty().getId());
//        Property property = review.getProperty();
//        property.setAverageRating(new java.math.BigDecimal(averageRating != null ? averageRating : 0));
//        propertyRepository.save(property);
//    }
//
//    private ReviewResponse mapToResponse(Review review) {
//        ReviewResponse response = new ReviewResponse();
//        response.setId(review.getId());
//        response.setPropertyId(review.getProperty().getId());
//        response.setUserId(review.getUser().getId());
//        response.setUserName(review.getUser().getUsername());
//        response.setRating(review.getRating());
//        response.setComment(review.getComment());
//        response.setCreatedAt(review.getCreatedAt());
//        return response;
//    }
//}
