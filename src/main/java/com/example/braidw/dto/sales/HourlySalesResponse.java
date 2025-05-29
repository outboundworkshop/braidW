package com.example.braidw.dto.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourlySalesResponse {
    private Map<String, Long> salesByHour;
    private Map<String, Integer> ordersByHour;
    private Map<String, Double> averageOrderByHour;
    private Map<String, Double> conversionRateByHour;
} 