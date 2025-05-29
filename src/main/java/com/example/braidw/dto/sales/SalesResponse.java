package com.example.braidw.dto.sales;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SalesResponse {
    private SalesSummary summary;
    private SalesGraph salesGraph;
    private ExpenseIncome expenseIncome;
    private CustomerVisit customerVisit;
    private List<KpiStatus> kpiStatus;

    @Data
    @Builder
    public static class SalesSummary {
        private String title;
        private String value;
        private String change;
        private Integer percentage;
        private String trend;  // "up" or "down"
    }

    @Data
    @Builder
    public static class SalesGraph {
        private Map<String, List<String>> dates;  // 일별/주별/월별 날짜
        private Map<String, List<Long>> values;   // 일별/주별/월별 매출액
    }

    @Data
    @Builder
    public static class ExpenseIncome {
        private Long expense;
        private Long income;
        private Integer expenseRatio;
        private Integer incomeRatio;
        private Integer expenseChange;
        private Integer incomeChange;
    }

    @Data
    @Builder
    public static class CustomerVisit {
        private List<String> dates;
        private List<Integer> newCustomers;
        private List<Integer> returningCustomers;
    }

    @Data
    @Builder
    public static class KpiStatus {
        private String kpi;
        private int target;
        private int current;
    }
} 