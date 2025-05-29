package com.example.braidw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private LocalDateTime createdAt;
    private String action;
    private String borderColor;  // UI 표시용 색상
    
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    
    private boolean isRead;
    
    public enum NotificationType {
        INVENTORY,      // 재고 관련
        SYSTEM,        // 시스템 관련
        SALES,         // 매출 관련
        CUSTOMER       // 고객 관련
    }
} 