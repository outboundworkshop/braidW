package com.example.braidw.controller.sales;

import com.example.braidw.dto.sales.DailySalesResponse;
import com.example.braidw.dto.sales.HourlySalesResponse;
import com.example.braidw.service.sales.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    
    private final SalesService salesService;

    @GetMapping("/daily-summary")
    public ResponseEntity<DailySalesResponse> getDailySalesSummary() {
        return ResponseEntity.ok(salesService.getDailySalesSummary());
    }

    @GetMapping("/hourly-stats")
    public ResponseEntity<HourlySalesResponse> getHourlySalesStats() {
        return ResponseEntity.ok(salesService.getHourlySalesStats());
    }

    @GetMapping("/monthly")
    public ResponseEntity<DailySalesResponse> getMonthlySalesSummary() {
        return ResponseEntity.ok(salesService.getMonthlySalesSummary());
    }

    @GetMapping("/comparison")
    public ResponseEntity<DailySalesResponse> getSalesComparison(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(salesService.getSalesComparison(startDate, endDate));
    }
} 