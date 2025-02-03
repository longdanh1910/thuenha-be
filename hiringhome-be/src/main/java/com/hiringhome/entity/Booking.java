//package com.hiringhome.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "bookings")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Booking {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "property_id", nullable = false)
//    private Property property;
//
//    @ManyToOne
//    @JoinColumn(name = "tenant_id", nullable = false)
//    private User tenant;
//
//    @Column(nullable = false)
//    private LocalDateTime checkInDate;
//
//    @Column(nullable = false)
//    private LocalDateTime checkOutDate;
//
//    @Column(precision = 10, scale = 2)
//    private BigDecimal totalPrice;
//
//    @Enumerated(EnumType.STRING)
//    private BookingStatus status;
//
//    private LocalDateTime checkedInAt;
//    private LocalDateTime checkedOutAt;
//    private LocalDateTime cancelledAt;
//    private String cancellationReason;
//
//    @PrePersist
//    protected void onCreate() {
//        if (status == null) {
//            status = BookingStatus.PENDING;
//        }
//    }
//}
//
