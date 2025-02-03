//package com.hiringhome.controller;
//
//import com.hiringhome.dto.booking.BookingRequest;
//import com.hiringhome.dto.booking.BookingResponse;
//import com.hiringhome.service.BookingService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/bookings")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
//public class BookingController {
//    private final BookingService bookingService;
//
//    @PostMapping
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
//        return ResponseEntity.ok(bookingService.createBooking(request));
//    }
//
//    @GetMapping("/my-bookings")
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<Page<BookingResponse>> getMyBookings(Pageable pageable) {
//        return ResponseEntity.ok(bookingService.getMyBookings(pageable));
//    }
//
//    @GetMapping("/my-properties-bookings")
//    @PreAuthorize("hasRole('LANDLORD')")
//    public ResponseEntity<Page<BookingResponse>> getMyPropertiesBookings(Pageable pageable) {
//        return ResponseEntity.ok(bookingService.getMyPropertiesBookings(pageable));
//    }
//
//    @PostMapping("/{id}/check-in")
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<BookingResponse> checkIn(@PathVariable Long id) {
//        return ResponseEntity.ok(bookingService.checkIn(id));
//    }
//
//    @PostMapping("/{id}/check-out")
//    @PreAuthorize("hasRole('TENANT')")
//    public ResponseEntity<BookingResponse> checkOut(@PathVariable Long id) {
//        return ResponseEntity.ok(bookingService.checkOut(id));
//    }
//
//    @PostMapping("/{id}/cancel")
//    public ResponseEntity<BookingResponse> cancelBooking(
//            @PathVariable Long id,
//            @RequestParam(required = false) String reason) {
//        return ResponseEntity.ok(bookingService.cancelBooking(id, reason));
//    }
//
//    @GetMapping("/monthly-income")
//    @PreAuthorize("hasRole('LANDLORD')")
//    public ResponseEntity<Double> getMonthlyIncome(
//            @RequestParam int year,
//            @RequestParam int month) {
//        return ResponseEntity.ok(bookingService.calculateMonthlyIncome(year, month));
//    }
//}
