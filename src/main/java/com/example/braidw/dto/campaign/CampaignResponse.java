package com.example.braidw.dto.campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignResponse {
    private List<Campaign> campaigns;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Campaign {
        private String title;
        private String status;
        private String statusColor;
        private Double progress;
        private List<Stat> stats;
        private String target;
        private String scheduledTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stat {
        private String label;
        private String value;
    }
} 