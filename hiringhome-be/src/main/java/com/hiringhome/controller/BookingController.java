package com.hiringhome.controller;

import com.hiringhome.dto.booking.BookingRequest;
import com.hiringhome.dto.booking.BookingResponse;
import com.hiringhome.entity.enums.BookingStatus;
import com.hiringhome.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(userDetails.getUsername(), request));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<BookingResponse>> getUserBookings(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        return ResponseEntity.ok(bookingService.getUserBookings(userDetails.getUsername(), pageable));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<Page<BookingResponse>> getPropertyBookings(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long propertyId,
            Pageable pageable) {
        return ResponseEntity.ok(bookingService.getPropertyBookings(userDetails.getUsername(), propertyId, pageable));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(userDetails.getUsername(), id, status));
    }
}
