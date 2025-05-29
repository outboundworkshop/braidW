package com.example.braidw.dto.support;

import com.example.braidw.entity.Support;
import com.example.braidw.entity.Support.SupportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SupportResponse {
    private Long id;
    private String name;
    private String institution;
    private String amount;
    private String interestRate;
    private LocalDate deadline;
    private String status;
    private String type;  // "대출" or "정부지원"
    private int recommendation;
    private List<String> reasons;
    private String image;
    
    public static SupportResponse from(Support support) {
        return SupportResponse.builder()
                .id(support.getId())
                .name(support.getName())
                .institution(support.getInstitution())
                .amount(support.getAmount())
                .interestRate(support.getInterestRate())
                .deadline(support.getDeadline())
                .status(support.getStatus())
                .type(support.getType().getDisplayName())
                .recommendation(support.getRecommendation())
                .reasons(support.getReasons())
                .image(support.getImage())
                .build();
    }
} 