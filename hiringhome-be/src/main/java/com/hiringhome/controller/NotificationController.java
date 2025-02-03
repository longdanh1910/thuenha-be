//package com.hiringhome.controller;
//
//import com.hiringhome.dto.notification.NotificationResponse;
//import com.hiringhome.service.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/notifications")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
//public class NotificationController {
//    private final NotificationService notificationService;
//
//    @GetMapping
//    public ResponseEntity<Page<NotificationResponse>> getMyNotifications(Pageable pageable) {
//        return ResponseEntity.ok(notificationService.getMyNotifications(pageable));
//    }
//
//    @GetMapping("/unread-count")
//    public ResponseEntity<Long> getUnreadCount() {
//        return ResponseEntity.ok(notificationService.getUnreadCount());
//    }
//
//    @PutMapping("/{id}/read")
//    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
//        notificationService.markAsRead(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/read-all")
//    public ResponseEntity<Void> markAllAsRead() {
//        notificationService.markAllAsRead();
//        return ResponseEntity.ok().build();
//    }
//}
