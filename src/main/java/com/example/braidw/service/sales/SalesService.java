package com.example.braidw.service.sales;

import com.example.braidw.dto.sales.SalesResponse;
import com.example.braidw.entity.Sales;
import com.example.braidw.entity.ExpenseIncome;
import com.example.braidw.entity.CustomerVisit;
import com.example.braidw.entity.KpiData;
import com.example.braidw.entity.User;
import com.example.braidw.repository.sales.SalesRepository;
import com.example.braidw.repository.sales.ExpenseIncomeRepository;
import com.example.braidw.repository.customer.CustomerVisitRepository;
import com.example.braidw.repository.kpi.KpiDataRepository;
import com.example.braidw.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesService {
    
    private final SalesRepository salesRepository;
    private final ExpenseIncomeRepository expenseIncomeRepository;
    private final CustomerVisitRepository customerVisitRepository;
    private final KpiDataRepository kpiDataRepository;
    private final UserRepository userRepository;
    
    public SalesResponse getSalesAnalytics(String userId, String period) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Map<String, List<String>> salesGraphDates = new HashMap<>();
        Map<String, List<Long>> salesGraphValues = new HashMap<>();
        
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime dailyStartDate = endDate.minusDays(7);
        LocalDateTime weeklyStartDate = endDate.minusWeeks(6);
        LocalDateTime monthlyStartDate = endDate.minusMonths(6);

        // 일별 데이터
        List<Sales> dailySales = salesRepository.findDailySalesByUser(user, dailyStartDate, endDate);
        salesGraphDates.put("일별", dailySales.stream()
                .map(s -> s.getDate().format(DateTimeFormatter.ofPattern("M/d")))
                .collect(Collectors.toList()));
        salesGraphValues.put("일별", dailySales.stream()
                .map(Sales::getAmount)
                .collect(Collectors.toList()));
        
        // 주별 데이터
        // 참고: 주별/월별 데이터 집계 방식에 대한 검토 필요. 현재는 일별 데이터를 그대로 사용하고 포맷만 변경.
        // 제대로 된 주별/월별 합계를 보려면 Group By 쿼리가 필요할 수 있음.
        List<Sales> weeklySales = salesRepository.findDailySalesByUser(user, weeklyStartDate, endDate);
        salesGraphDates.put("주별", weeklySales.stream()
                .map(s -> s.getDate().format(DateTimeFormatter.ofPattern("'M월 W주'")))
                .distinct() // 포맷팅 후 중복 제거 (단순 포맷팅 시 주차가 같으면 중복될 수 있음)
                .collect(Collectors.toList()));
        // 주별 매출 합계 (간단한 방식, 정확한 주별 집계는 아님)
        salesGraphValues.put("주별", weeklySales.stream()
                .collect(Collectors.groupingBy(s -> s.getDate().format(DateTimeFormatter.ofPattern("'M월 W주'")),
                         Collectors.summingLong(Sales::getAmount)))
                .values().stream().collect(Collectors.toList()));
        
        // 월별 데이터
        List<Sales> monthlySales = salesRepository.findDailySalesByUser(user, monthlyStartDate, endDate);
        salesGraphDates.put("월별", monthlySales.stream()
                .map(s -> s.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .distinct() // 포맷팅 후 중복 제거
                .collect(Collectors.toList()));
        // 월별 매출 합계 (간단한 방식, 정확한 월별 집계는 아님)
        salesGraphValues.put("월별", monthlySales.stream()
                .collect(Collectors.groupingBy(s -> s.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                         Collectors.summingLong(Sales::getAmount)))
                .values().stream().collect(Collectors.toList()));
        
        SalesResponse.SalesGraph salesGraph = SalesResponse.SalesGraph.builder()
                .dates(salesGraphDates)
                .values(salesGraphValues)
                .build();

        // 2. Expense/Income Data
        YearMonth currentMonth = YearMonth.now();
        Optional<ExpenseIncome> expenseIncomeDataOpt = expenseIncomeRepository.findByMonthAndUser(currentMonth, user);
        SalesResponse.ExpenseIncome expenseIncomeResponse = expenseIncomeDataOpt
            .map(data -> SalesResponse.ExpenseIncome.builder()
                .expense(data.getExpense())
                .income(data.getIncome())
                .expenseRatio(data.getExpenseRatio())
                .incomeRatio(data.getIncomeRatio())
                .expenseChange(data.getExpenseChange())
                .incomeChange(data.getIncomeChange())
                .build())
            .orElse(null); // Or provide a default empty ExpenseIncome object

        // 3. Customer Visit Data (e.g., last 7 days)
        List<CustomerVisit> visits = customerVisitRepository
                .findByVisitDateBetweenAndUserOrderByVisitDateAsc(user,
                        LocalDateTime.now().minusDays(7), LocalDateTime.now());
        SalesResponse.CustomerVisit customerVisitResponse = SalesResponse.CustomerVisit.builder()
                .dates(visits.stream()
                        .map(visit -> visit.getVisitDate().format(DateTimeFormatter.ofPattern("E")))
                        .collect(Collectors.toList()))
                .newCustomers(visits.stream()
                        .map(CustomerVisit::getNewCustomerCount)
                        .collect(Collectors.toList()))
                .returningCustomers(visits.stream()
                        .map(CustomerVisit::getReturningCustomerCount)
                        .collect(Collectors.toList()))
                .build();

        // 4. KPI Status Data
        List<KpiData> kpiDataList = kpiDataRepository.findAllByUserOrderByCreatedAtDesc(user);
        List<SalesResponse.KpiStatus> kpiStatusResponse = kpiDataList.stream()
                .map(data -> SalesResponse.KpiStatus.builder()
                        .kpi(data.getKpiName())
                        .target(data.getTarget())
                        .current(data.getCurrent())
                        .build())
                .collect(Collectors.toList());
        
        // 5. Sales Summary Data (Example - this would need more specific logic)
        // For now, leaving it null or with placeholder data as its calculation isn't defined here yet.
        SalesResponse.SalesSummary salesSummary = null; // Placeholder

        return SalesResponse.builder()
                .summary(salesSummary)
                .salesGraph(salesGraph)
                .expenseIncome(expenseIncomeResponse)
                .customerVisit(customerVisitResponse)
                .kpiStatus(kpiStatusResponse)
                .build();
    }
    
    public SalesResponse getExpenseIncomeAnalytics(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        YearMonth currentMonth = YearMonth.now();
        ExpenseIncome data = expenseIncomeRepository.findByMonthAndUser(currentMonth, user)
                .orElseThrow(() -> new RuntimeException("Current month expense/income data not found for user: " + userId));
        
        return SalesResponse.builder()
                .expenseIncome(SalesResponse.ExpenseIncome.builder()
                .expense(data.getExpense())
                .income(data.getIncome())
                        .expenseRatio(data.getExpenseRatio())
                        .incomeRatio(data.getIncomeRatio())
                        .expenseChange(data.getExpenseChange())
                        .incomeChange(data.getIncomeChange())
                        .build())
                .build();
    }
    
    public SalesResponse getVisitAnalytics(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<CustomerVisit> visits = customerVisitRepository
                .findByVisitDateBetweenAndUserOrderByVisitDateAsc(user,
                        LocalDateTime.now().minusDays(7), LocalDateTime.now());
        
        return SalesResponse.builder()
                .customerVisit(SalesResponse.CustomerVisit.builder()
                .dates(visits.stream()
                        .map(visit -> visit.getVisitDate().format(DateTimeFormatter.ofPattern("E")))
                        .collect(Collectors.toList()))
                .newCustomers(visits.stream()
                        .map(CustomerVisit::getNewCustomerCount)
                        .collect(Collectors.toList()))
                .returningCustomers(visits.stream()
                        .map(CustomerVisit::getReturningCustomerCount)
                        .collect(Collectors.toList()))
                        .build())
                .build();
    }
    
    public SalesResponse getKpiAnalytics(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<KpiData> kpiDataList = kpiDataRepository.findAllByUserOrderByCreatedAtDesc(user);
        
        List<SalesResponse.KpiStatus> kpiStatus = kpiDataList.stream()
                .map(data -> SalesResponse.KpiStatus.builder()
                        .kpi(data.getKpiName())
                        .target(data.getTarget())
                        .current(data.getCurrent())
                        .build())
                .collect(Collectors.toList());

        return SalesResponse.builder()
                .kpiStatus(kpiStatus)
                .build();
    }

    public Sales saveSales(Sales sales, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        sales.setUser(user);
        return salesRepository.save(sales);
    }
} 