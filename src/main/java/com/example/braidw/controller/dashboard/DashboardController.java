package com.example.braidw.controller.dashboard;

import com.example.braidw.dto.sales.SalesResponse;
//import com.example.braidw.dto.campaign.CampaignResponse;
import com.example.braidw.dto.support.SupportResponse;
import com.example.braidw.service.sales.SalesService;
import com.example.braidw.service.support.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final SalesService salesService;
    //private final CampaignService campaignService;
    private final SupportService supportService;

    @GetMapping("/summary")
    public ResponseEntity<SalesResponse> getSummary() {
        return ResponseEntity.ok(salesService.getSalesAnalytics("day"));
    }

//    @GetMapping("/campaigns")
//    public ResponseEntity<List<CampaignResponse>> getCampaigns() {
//        return ResponseEntity.ok(campaignService.getActiveCampaigns());
//    }
//
//    @GetMapping("/notifications")
//    public ResponseEntity<List<CampaignResponse>> getNotifications() {
//        return ResponseEntity.ok(campaignService.getCampaignSummary());
//    }

    @GetMapping("/supports")
    public ResponseEntity<List<SupportResponse>> getSupports() {
        return ResponseEntity.ok(supportService.getAllSupports());
    }
} 