package com.hiringhome.dto.review;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long bookingId;
    private Long propertyId;
    private String propertyTitle;
    private Long userId;
    private String userFullName;
    private String userAvatarUrl;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
