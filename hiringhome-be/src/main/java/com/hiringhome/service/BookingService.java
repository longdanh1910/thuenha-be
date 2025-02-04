package com.hiringhome.service;

import com.hiringhome.dto.booking.BookingRequest;
import com.hiringhome.dto.booking.BookingResponse;
import com.hiringhome.entity.Booking;
import com.hiringhome.entity.Property;
import com.hiringhome.entity.User;
import com.hiringhome.entity.enums.BookingStatus;
import com.hiringhome.entity.enums.NotificationType;
import com.hiringhome.repository.BookingRepository;
import com.hiringhome.repository.PropertyRepository;
import com.hiringhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public BookingResponse createBooking(String username, BookingRequest request) {
        User tenant = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (property.getOwner().getId().equals(tenant.getId())) {
            throw new RuntimeException("You cannot book your own property");
        }

        if (bookingRepository.existsOverlappingBooking(
                property.getId(), request.getCheckInDate(), request.getCheckOutDate())) {
            throw new RuntimeException("Property is not available for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        if (nights < 1) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        double totalPrice = property.getPrice() * nights;

        Booking booking = new Booking();
        booking.setProperty(property);
        booking.setTenant(tenant);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);
        booking.setGuests(request.getGuests());

        Booking savedBooking = bookingRepository.save(booking);

        // Notify property owner
        notificationService.createNotification(
                property.getOwner().getId(),
                NotificationType.NEW_BOOKING,
                "New booking request",
                String.format("New booking request for %s from %s to %s",
                        property.getTitle(),
                        request.getCheckInDate(),
                        request.getCheckOutDate()),
                savedBooking.getId()
        );

        return toBookingResponse(savedBooking);
    }

    public Page<BookingResponse> getUserBookings(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByTenantOrderByCreatedAtDesc(user, pageable)
                .map(this::toBookingResponse);
    }

    public Page<BookingResponse> getPropertyBookings(String username, Long propertyId, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You can only view bookings for your own properties");
        }

        return bookingRepository.findByPropertyOrderByCreatedAtDesc(property, pageable)
                .map(this::toBookingResponse);
    }

    public BookingResponse updateBookingStatus(String username, Long bookingId, BookingStatus status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getProperty().getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You can only update status for bookings of your properties");
        }

        booking.setStatus(status);
        
        // Notify tenant
        notificationService.createNotification(
                booking.getTenant().getId(),
                NotificationType.BOOKING_STATUS_UPDATED,
                "Booking status updated",
                String.format("Your booking for %s has been %s",
                        booking.getProperty().getTitle(),
                        status.toString()),
                bookingId
        );

        return toBookingResponse(bookingRepository.save(booking));
    }

    private BookingResponse toBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .propertyId(booking.getProperty().getId())
                .propertyTitle(booking.getProperty().getTitle())
                .propertyImage(booking.getProperty().getImages().isEmpty() ? null :
                        booking.getProperty().getImages().get(0).getImageUrl())
                .tenantId(booking.getTenant().getId())
                .tenantName(booking.getTenant().getFullName())
                .tenantAvatar(booking.getTenant().getAvatarUrl())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .guests(booking.getGuests())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
