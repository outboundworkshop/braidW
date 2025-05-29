package com.example.braidw.dto.reward;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardPointResponse {
    private String userId;
    private Integer points;
} 