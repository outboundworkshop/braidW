package com.example.braidw.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id", length = 50)
    private String id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String location;

    @Column(name = "consent_given", nullable = true)
    @Builder.Default
    private Boolean consentGiven = false;

    @Column(nullable = false)
    private String password;

    @Column(name = "current_points", nullable = false)
    @Builder.Default
    private Integer currentPoints = 0;

    @Column(name = "total_points_earned", nullable = false)
    @Builder.Default
    private Integer totalPointsEarned = 0;

    @Column(name = "total_points_used", nullable = false)
    @Builder.Default
    private Integer totalPointsUsed = 0;

    @Column(name = "last_point_earned_at")
    private LocalDateTime lastPointEarnedAt;

    @Column(name = "last_point_used_at")
    private LocalDateTime lastPointUsedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 