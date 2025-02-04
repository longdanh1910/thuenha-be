package com.hiringhome.dto.property;

import com.hiringhome.entity.enums.PropertyStatus;
import com.hiringhome.entity.enums.PropertyType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PropertyResponse {
    private Long id;
    private String title;
    private String description;
    private String address;
    private Double price;
    private Double area;
    private Integer numberOfRooms;
    private PropertyType propertyType;
    private List<String> amenities;
    private PropertyStatus status;
    private Double averageRating;
    private Integer totalReviews;
    private List<String> images;
    private String ownerName;
    private String ownerAvatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
