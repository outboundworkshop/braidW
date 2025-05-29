package com.example.braidw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String status;
    private String statusColor;
    private Integer progress;
    
    private Integer sentCount;
    private Integer openCount;
    private Integer clickCount;
    
    private String target;
    private LocalDateTime scheduledTime;
    
    @Enumerated(EnumType.STRING)
    private CampaignType type;
    
    public enum CampaignType {
        CAMPAIGN,
        NOTIFICATION
    }
} 