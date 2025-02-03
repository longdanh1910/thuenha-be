//package com.hiringhome.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "properties")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "owner_id", nullable = false)
//    private User owner;
//
//    private String title;
//    private String description;
//    private String address;
//
//    @Column(precision = 10, scale = 2)
//    private BigDecimal pricePerNight;
//
//    @Enumerated(EnumType.STRING)
//    private PropertyStatus status;
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PropertyImage> images = new ArrayList<>();
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
//    private List<Booking> bookings = new ArrayList<>();
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
//    private List<Review> reviews = new ArrayList<>();
//
//    private Integer maxGuests;
//    private Integer bedrooms;
//    private Integer bathrooms;
//
//    // Location coordinates for map display
//    private Double latitude;
//    private Double longitude;
//
//    private Integer totalBookings = 0;
//
//    @Column(precision = 3, scale = 2)
//    private BigDecimal averageRating = BigDecimal.ZERO;
//}
//
