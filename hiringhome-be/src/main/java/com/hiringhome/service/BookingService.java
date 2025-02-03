//package com.hiringhome.service;
//
//import com.hiringhome.dto.booking.BookingRequest;
//import com.hiringhome.dto.booking.BookingResponse;
//import com.hiringhome.entity.Booking;
//import com.hiringhome.entity.Property;
//import com.hiringhome.entity.User;
//import com.hiringhome.repository.BookingRepository;
//import com.hiringhome.repository.PropertyRepository;
//import com.hiringhome.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class BookingService {
//    private final BookingRepository bookingRepository;
//    private final PropertyRepository propertyRepository;
//    private final UserRepository userRepository;
//    private final NotificationService notificationService;
//
//    @Transactional
//    public BookingResponse createBooking(BookingRequest request) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User tenant = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Property property = propertyRepository.findById(request.getPropertyId())
//                .orElseThrow(() -> new RuntimeException("Property not found"));
//
//        // Validate booking dates
//        if (request.getCheckInDate().isAfter(request.getCheckOutDate())) {
//            throw new IllegalArgumentException("Check-in date must be before check-out date");
//        }
//
//        // Check for overlapping bookings
//        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
//                property.getId(), request.getCheckInDate(), request.getCheckOutDate());
//        if (!overlappingBookings.isEmpty()) {
//            throw new RuntimeException("Property is not available for the selected dates");
//        }
//
//        // Calculate total price
//        long days = java.time.temporal.ChronoUnit.DAYS.between(
//                request.getCheckInDate(), request.getCheckOutDate());
//        if (days < 1) days = 1;
//
//        Booking booking = new Booking();
//        booking.setProperty(property);
//        booking.setTenant(tenant);
//        booking.setCheckInDate(request.getCheckInDate());
//        booking.setCheckOutDate(request.getCheckOutDate());
//        booking.setTotalPrice(property.getPricePerNight().multiply(new java.math.BigDecimal(days)));
//        booking.setStatus(BookingStatus.PENDING);
//
//        Booking savedBooking = bookingRepository.save(booking);
//
//        // Send notification to property owner
//        notificationService.sendBookingNotification(property.getOwner(), savedBooking);
//
//        return mapToResponse(savedBooking);
//    }
//
//    public Page<BookingResponse> getMyBookings(Pageable pageable) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return bookingRepository.findByTenantId(user.getId(), pageable)
//                .map(this::mapToResponse);
//    }
//
//    public Page<BookingResponse> getMyPropertiesBookings(Pageable pageable) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User owner = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return bookingRepository.findByPropertyOwnerId(owner.getId(), pageable)
//                .map(this::mapToResponse);
//    }
//
//    @Transactional
//    public BookingResponse cancelBooking(Long bookingId, String reason) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        // Validate cancellation time
//        if (booking.getCheckInDate().minusDays(1).isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Cannot cancel booking less than 24 hours before check-in");
//        }
//
//        booking.setStatus(BookingStatus.CANCELLED);
//        booking.setCancelledAt(LocalDateTime.now());
//        booking.setCancellationReason(reason);
//
//        Booking savedBooking = bookingRepository.save(booking);
//
//        // Send notification
//        if (booking.getTenant().equals(getCurrentUser())) {
//            notificationService.sendBookingCancellationNotification(booking.getProperty().getOwner(), savedBooking);
//        } else {
//            notificationService.sendBookingCancellationNotification(booking.getTenant(), savedBooking);
//        }
//
//        return mapToResponse(savedBooking);
//    }
//
//    public BookingResponse checkIn(Long bookingId) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        if (!booking.getStatus().equals(BookingStatus.CONFIRMED)) {
//            throw new RuntimeException("Booking must be confirmed before check-in");
//        }
//
//        booking.setStatus(BookingStatus.CHECKED_IN);
//        booking.setCheckedInAt(LocalDateTime.now());
//
//        return mapToResponse(bookingRepository.save(booking));
//    }
//
//    public BookingResponse checkOut(Long bookingId) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        if (!booking.getStatus().equals(BookingStatus.CHECKED_IN)) {
//            throw new RuntimeException("Booking must be checked in before check-out");
//        }
//
//        booking.setStatus(BookingStatus.COMPLETED);
//        booking.setCheckedOutAt(LocalDateTime.now());
//
//        Property property = booking.getProperty();
//        property.setTotalBookings(property.getTotalBookings() + 1);
//        propertyRepository.save(property);
//
//        return mapToResponse(bookingRepository.save(booking));
//    }
//
//    public Double calculateMonthlyIncome(int year, int month) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User owner = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return bookingRepository.calculateMonthlyIncome(owner.getId(), year, month);
//    }
//
//    private User getCurrentUser() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    private BookingResponse mapToResponse(Booking booking) {
//        BookingResponse response = new BookingResponse();
//        response.setId(booking.getId());
//        response.setPropertyId(booking.getProperty().getId());
//        response.setPropertyTitle(booking.getProperty().getTitle());
//        response.setPropertyAddress(booking.getProperty().getAddress());
//        response.setTenantName(booking.getTenant().getUsername());
//        response.setTenantId(booking.getTenant().getId());
//        response.setCheckInDate(booking.getCheckInDate());
//        response.setCheckOutDate(booking.getCheckOutDate());
//        response.setTotalPrice(booking.getTotalPrice());
//        response.setStatus(booking.getStatus());
//        response.setCheckedInAt(booking.getCheckedInAt());
//        response.setCheckedOutAt(booking.getCheckedOutAt());
//        response.setCancelledAt(booking.getCancelledAt());
//        response.setCancellationReason(booking.getCancellationReason());
//        return response;
//    }
//}
