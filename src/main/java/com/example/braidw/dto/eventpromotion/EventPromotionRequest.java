package com.example.braidw.dto.eventpromotion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventPromotionRequest {
    private String title;
    private String description;
    private String startDate;
    private String endDate;
} 