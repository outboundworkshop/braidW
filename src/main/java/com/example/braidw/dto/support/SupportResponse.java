package com.example.braidw.dto.support;

import com.example.braidw.entity.Support;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class SupportResponse {
    private String title;
    private String description;
    private String deadline;
    private String recommendationReason;
    private String requirements;
    private String applicationUrl;

    public static SupportResponse from(Support support) {
        return SupportResponse.builder()
                .title(support.getName())
                .description(String.format("%s (금리 %s)", support.getAmount(), support.getInterestRate()))
                .deadline(support.getDeadline().format(DateTimeFormatter.ISO_DATE))
                .recommendationReason(String.join("\n", support.getReasons()))
                .requirements(String.join(", ", support.getReasons()))
                .applicationUrl(support.getInstitution())
                .build();
    }
} 