package com.example.braidw.dto.reward;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardRequest {
    private String userId;
    private String action;
} 