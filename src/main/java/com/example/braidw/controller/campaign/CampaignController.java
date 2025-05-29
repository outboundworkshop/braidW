//package com.example.braidw.controller.campaign;
//
//import com.example.braidw.dto.campaign.CampaignResponse;
//import com.example.braidw.service.campaign.CampaignService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/campaigns")
//@RequiredArgsConstructor
//public class CampaignController {
//
//    private final CampaignService campaignService;
//
//    @GetMapping("/active")
//    public ResponseEntity<List<CampaignResponse>> getActiveCampaigns() {
//        return ResponseEntity.ok(campaignService.getActiveCampaigns());
//    }
//
//    @GetMapping("/{campaignId}/progress")
//    public ResponseEntity<CampaignResponse> getCampaignProgress(@PathVariable Long campaignId) {
//        return ResponseEntity.ok(campaignService.getCampaignProgress(campaignId));
//    }
//
//    @GetMapping("/summary")
//    public ResponseEntity<CampaignResponse> getCampaignSummary() {
//        return ResponseEntity.ok(campaignService.getCampaignSummary());
//    }
//
//    @GetMapping("/statistics")
//    public ResponseEntity<CampaignResponse> getCampaignStatistics() {
//        return ResponseEntity.ok(campaignService.getCampaignStatistics());
//    }
//}