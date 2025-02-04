package com.hiringhome.controller;

import com.hiringhome.dto.review.CreateReviewRequest;
import com.hiringhome.dto.review.ReviewResponse;
import com.hiringhome.dto.review.UpdateReviewRequest;
import com.hiringhome.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(userDetails.getUsername(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody UpdateReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(userDetails.getUsername(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        reviewService.deleteReview(userDetails.getUsername(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<Page<ReviewResponse>> getPropertyReviews(
            @PathVariable Long propertyId,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId, pageable));
    }
}
