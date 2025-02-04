package com.hiringhome.dto.property;

import com.hiringhome.entity.enums.PropertyStatus;
import com.hiringhome.entity.enums.PropertyType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePropertyRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Area is required")
    @Min(value = 1, message = "Area must be greater than 0")
    private Double area;

    @NotNull(message = "Number of rooms is required")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    private Integer numberOfRooms;

    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    private List<String> amenities;

    @NotNull(message = "Status is required")
    private PropertyStatus status;
}
