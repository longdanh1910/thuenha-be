//package com.hiringhome.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "property_images")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PropertyImage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "property_id", nullable = false)
//    private Property property;
//
//    @Column(nullable = false)
//    private String imageUrl;
//
//    private String description;
//    private Boolean isPrimary = false;
//}
