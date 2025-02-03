//package com.hiringhome.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "notifications")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Notification {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false)
//    private String message;
//
//    @Enumerated(EnumType.STRING)
//    private NotificationType type;
//
//    private boolean isRead = false;
//    private LocalDateTime createdAt;
//    private String actionUrl;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//    }
//}
//
