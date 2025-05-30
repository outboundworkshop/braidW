package com.example.braidw.controller.sales;

import com.example.braidw.dto.sales.SalesResponse;
import com.example.braidw.service.sales.SalesService;
import com.example.braidw.security.SecurityUtils;
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
        String userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(salesService.getSalesAnalytics(userId, period));
    }

    @GetMapping("/expense-income")
    public ResponseEntity<SalesResponse> getExpenseIncomeAnalytics() {
        String userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(salesService.getExpenseIncomeAnalytics(userId));
    }

    @GetMapping("/visits")
    public ResponseEntity<SalesResponse> getVisitAnalytics() {
        String userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(salesService.getVisitAnalytics(userId));
    }

    @GetMapping("/kpi")
    public ResponseEntity<SalesResponse> getKpiAnalytics() {
        String userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(salesService.getKpiAnalytics(userId));
    }
} 