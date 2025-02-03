//package com.hiringhome.controller;
//
//import com.hiringhome.dto.review.ReviewRequest;
//import com.hiringhome.dto.review.ReviewResponse;
//import com.hiringhome.service.ReviewService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/reviews")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
//public class ReviewController {
//    private final ReviewService reviewService;
//
//    @PostMapping("/properties/{propertyId}")
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<ReviewResponse> createReview(
//            @PathVariable Long propertyId,
//            @Valid @RequestBody ReviewRequest request) {
//        return ResponseEntity.ok(reviewService.createReview(propertyId, request));
//    }
//
//    @GetMapping("/properties/{propertyId}")
//    public ResponseEntity<Page<ReviewResponse>> getPropertyReviews(
//            @PathVariable Long propertyId,
//            Pageable pageable) {
//        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId, pageable));
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
//        reviewService.deleteReview(id);
//        return ResponseEntity.ok().build();
//    }
//}
