package com.example.braidw.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySalesResponse {
    private Long totalSales;
    private Integer totalOrders;
    private Double averageOrderAmount;
    private Double conversionRate;
    private Double salesGrowthRate;
    private Double ordersGrowthRate;
    private Double averageOrderGrowthRate;
    private Double conversionRateGrowthRate;
} 