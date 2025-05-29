package com.example.braidw.dto.campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double progressRate;
    private Long targetValue;
    private Long currentValue;
    private Long remainingValue;
    private String status;
    private String type;
    private Integer participantCount;
    private Double conversionRate;
    private Double effectivenessRate;
} 