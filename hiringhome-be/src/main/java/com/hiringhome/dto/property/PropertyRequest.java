package com.hiringhome.dto.property;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PropertyRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Price per night is required")
    @Min(value = 0, message = "Price must be greater than 0")
    private BigDecimal pricePerNight;

    @NotNull(message = "Maximum guests is required")
    @Min(value = 1, message = "Maximum guests must be at least 1")
    private Integer maxGuests;

    @NotNull(message = "Number of bedrooms is required")
    @Min(value = 1, message = "Number of bedrooms must be at least 1")
    private Integer bedrooms;

    @NotNull(message = "Number of bathrooms is required")
    @Min(value = 1, message = "Number of bathrooms must be at least 1")
    private Integer bathrooms;

    private Double latitude;
    private Double longitude;
}
