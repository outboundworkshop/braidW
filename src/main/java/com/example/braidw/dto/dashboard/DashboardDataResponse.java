package com.example.braidw.dto.dashboard;

import com.example.braidw.dto.sales.SalesResponse;
import com.example.braidw.dto.support.SupportResponse;
// import com.example.braidw.dto.campaign.CampaignResponse; // If campaigns are added
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardDataResponse {
    private SalesResponse salesSummary;
    private List<SupportResponse> recommendedSupports;
    // private List<CampaignResponse> activeCampaigns;
} 