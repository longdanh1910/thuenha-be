package com.hiringhome.dto.property;

import lombok.Data;

@Data
public class PropertySearchRequest {
    private String location;
    private Double minPrice;
    private Double maxPrice;
    private Integer minBedrooms;
    private Integer maxBedrooms;
    private Double minArea;
    private Double maxArea;
    private String amenities;
    private String sortBy;
    private String sortDirection;
}
