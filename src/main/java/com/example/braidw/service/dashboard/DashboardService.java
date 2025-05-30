package com.example.braidw.service.dashboard;

import com.example.braidw.dto.dashboard.DashboardDataResponse;
import com.example.braidw.dto.sales.SalesResponse;
import com.example.braidw.dto.support.SupportResponse;
import com.example.braidw.service.sales.SalesService;
import com.example.braidw.service.support.SupportService;
// Import CampaignService and CampaignResponse if needed later
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final SalesService salesService;
    private final SupportService supportService;
    // private final CampaignService campaignService; // If campaigns are added

    public DashboardDataResponse getDashboardData(String userId) {
        // In a real scenario, userId might be used to filter data in underlying services
        // For now, assuming services fetch all relevant data or handle user scoping internally
        
        SalesResponse salesSummary = salesService.getSalesAnalytics("day"); // Example period
        List<SupportResponse> recommendedSupports = supportService.recommendSupports(null, null, null, null); // Example: no specific criteria
        // List<CampaignResponse> activeCampaigns = campaignService.getActiveCampaigns(userId); // Example

        return DashboardDataResponse.builder()
                .salesSummary(salesSummary)
                .recommendedSupports(recommendedSupports)
                // .activeCampaigns(activeCampaigns)
                .build();
    }
} 