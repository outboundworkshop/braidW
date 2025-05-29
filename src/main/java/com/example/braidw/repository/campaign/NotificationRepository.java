package com.example.braidw.repository;

import com.example.braidw.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime after);
    List<Notification> findByIsReadFalseOrderByCreatedAtDesc();
    List<Notification> findByTypeOrderByCreatedAtDesc(Notification.NotificationType type);
} 