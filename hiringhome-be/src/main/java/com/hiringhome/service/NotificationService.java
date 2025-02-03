//package com.hiringhome.service;
//
//import com.hiringhome.dto.notification.NotificationResponse;
//import com.hiringhome.entity.Booking;
//import com.hiringhome.entity.Notification;
//import com.hiringhome.entity.Review;
//import com.hiringhome.entity.User;
//import com.hiringhome.repository.NotificationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class NotificationService {
//    private final NotificationRepository notificationRepository;
//
//    @Transactional
//    public void sendBookingNotification(User recipient, Booking booking) {
//        Notification notification = new Notification();
//        notification.setRecipient(recipient);
//        notification.setType(NotificationType.BOOKING);
//        notification.setTitle("New Booking Request");
//        notification.setContent(String.format("New booking request for %s from %s to %s",
//                booking.getProperty().getTitle(),
//                booking.getCheckInDate().toLocalDate(),
//                booking.getCheckOutDate().toLocalDate()));
//        notification.setReferenceId(booking.getId());
//        notification.setRead(false);
//        notification.setCreatedAt(LocalDateTime.now());
//
//        notificationRepository.save(notification);
//    }
//
//    @Transactional
//    public void sendBookingCancellationNotification(User recipient, Booking booking) {
//        Notification notification = new Notification();
//        notification.setRecipient(recipient);
//        notification.setType(NotificationType.BOOKING_CANCELLATION);
//        notification.setTitle("Booking Cancelled");
//        notification.setContent(String.format("Booking for %s has been cancelled. Reason: %s",
//                booking.getProperty().getTitle(),
//                booking.getCancellationReason()));
//        notification.setReferenceId(booking.getId());
//        notification.setRead(false);
//        notification.setCreatedAt(LocalDateTime.now());
//
//        notificationRepository.save(notification);
//    }
//
//    @Transactional
//    public void sendReviewNotification(User recipient, Review review) {
//        Notification notification = new Notification();
//        notification.setRecipient(recipient);
//        notification.setType(NotificationType.REVIEW);
//        notification.setTitle("New Review");
//        notification.setContent(String.format("New %d-star review for %s: %s",
//                review.getRating(),
//                review.getProperty().getTitle(),
//                review.getComment()));
//        notification.setReferenceId(review.getId());
//        notification.setRead(false);
//        notification.setCreatedAt(LocalDateTime.now());
//
//        notificationRepository.save(notification);
//    }
//
//    public Page<NotificationResponse> getMyNotifications(Pageable pageable) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return notificationRepository.findByRecipientUsernameOrderByCreatedAtDesc(username, pageable)
//                .map(this::mapToResponse);
//    }
//
//    public void markAsRead(Long notificationId) {
//        Notification notification = notificationRepository.findById(notificationId)
//                .orElseThrow(() -> new RuntimeException("Notification not found"));
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (!notification.getRecipient().getUsername().equals(username)) {
//            throw new RuntimeException("You can only mark your own notifications as read");
//        }
//
//        notification.setRead(true);
//        notificationRepository.save(notification);
//    }
//
//    public void markAllAsRead() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        notificationRepository.markAllAsRead(username);
//    }
//
//    private NotificationResponse mapToResponse(Notification notification) {
//        NotificationResponse response = new NotificationResponse();
//        response.setId(notification.getId());
//        response.setType(notification.getType());
//        response.setTitle(notification.getTitle());
//        response.setContent(notification.getContent());
//        response.setReferenceId(notification.getReferenceId());
//        response.setRead(notification.isRead());
//        response.setCreatedAt(notification.getCreatedAt());
//        return response;
//    }
//}
