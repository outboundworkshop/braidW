package com.example.braidw.dto.notification;

import com.example.braidw.entity.Notification.NotificationType;
import lombok.Data;

@Data
public class CreateNotificationRequest {
    private String title;
    private String message;
    private NotificationType type;
    private String action; // e.g., a URL to navigate to
} 