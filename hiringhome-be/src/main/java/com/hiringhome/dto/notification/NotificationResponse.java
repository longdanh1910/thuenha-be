package com.hiringhome.dto.notification;

import com.hiringhome.entity.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {
    private Long id;
    private NotificationType type;
    private String title;
    private String content;
    private Long referenceId;
    private boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
