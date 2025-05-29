package com.example.braidw.dto.reward;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardResponse {
    private String message;
    private Integer currentPoints;
} 