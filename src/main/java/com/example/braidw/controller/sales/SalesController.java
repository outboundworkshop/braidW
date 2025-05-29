package com.example.braidw.controller.sales;

import com.example.braidw.dto.sales.SalesResponse;
import com.example.braidw.service.sales.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class SalesController {
    
    private final SalesService salesService;

    @GetMapping("/sales")
    public ResponseEntity<SalesResponse> getSalesAnalytics(
            @RequestParam(defaultValue = "day") String period) {
        return ResponseEntity.ok(salesService.getSalesAnalytics(period));
    }

    @GetMapping("/expense-income")
    public ResponseEntity<SalesResponse> getExpenseIncomeAnalytics() {
        return ResponseEntity.ok(salesService.getExpenseIncomeAnalytics());
    }

    @GetMapping("/visits")
    public ResponseEntity<SalesResponse> getVisitAnalytics() {
        return ResponseEntity.ok(salesService.getVisitAnalytics());
    }

    @GetMapping("/kpi")
    public ResponseEntity<SalesResponse> getKpiAnalytics() {
        return ResponseEntity.ok(salesService.getKpiAnalytics());
    }
} 