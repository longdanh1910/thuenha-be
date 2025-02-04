package com.hiringhome.dto.booking;

import com.hiringhome.entity.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long propertyId;
    private String propertyTitle;
    private String propertyImage;
    private Long tenantId;
    private String tenantName;
    private String tenantAvatar;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double totalPrice;
    private Integer guests;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
