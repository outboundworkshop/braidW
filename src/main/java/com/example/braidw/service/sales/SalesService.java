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
    
    public SalesResponse getSalesAnalytics(String period) {
        Map<String, List<String>> dates = new HashMap<>();
        Map<String, List<Long>> values = new HashMap<>();
        
        // 일별 데이터
        List<Sales> dailySales = salesRepository.findDailySales(
                LocalDateTime.now().minusDays(7), LocalDateTime.now());
        dates.put("일별", dailySales.stream()
                .map(s -> s.getDate().format(DateTimeFormatter.ofPattern("M/d")))
                .collect(Collectors.toList()));
        values.put("일별", dailySales.stream()
                .map(Sales::getAmount)
                .collect(Collectors.toList()));
        
        // 주별 데이터
        List<Sales> weeklySales = salesRepository.findDailySales(
                LocalDateTime.now().minusWeeks(6), LocalDateTime.now());
        dates.put("주별", weeklySales.stream()
                .map(s -> s.getDate().format(DateTimeFormatter.ofPattern("'M월 W주'")))
                .collect(Collectors.toList()));
        values.put("주별", weeklySales.stream()
                .map(Sales::getAmount)
                .collect(Collectors.toList()));
        
        // 월별 데이터
        List<Sales> monthlySales = salesRepository.findDailySales(
                LocalDateTime.now().minusMonths(6), LocalDateTime.now());
        dates.put("월별", monthlySales.stream()
                .map(s -> s.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .collect(Collectors.toList()));
        values.put("월별", monthlySales.stream()
                .map(Sales::getAmount)
                .collect(Collectors.toList()));
        
        return SalesResponse.builder()
                .salesGraph(SalesResponse.SalesGraph.builder()
                .dates(dates)
                .values(values)
                        .build())
                .build();
    }
    
    public SalesResponse getExpenseIncomeAnalytics() {
        YearMonth currentMonth = YearMonth.now();
        ExpenseIncome data = expenseIncomeRepository.findByMonth(currentMonth)
                .orElseThrow(() -> new RuntimeException("Current month expense/income data not found"));
        
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
    
    public SalesResponse getVisitAnalytics() {
        List<CustomerVisit> visits = customerVisitRepository
                .findByVisitDateBetweenOrderByVisitDateAsc(
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
    
    public SalesResponse getKpiAnalytics() {
        List<KpiData> kpiDataList = kpiDataRepository.findAllByOrderByCreatedAtDesc();
        
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