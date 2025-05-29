package com.example.braidw.service.sales;

import com.example.braidw.dto.sales.SalesResponse;
import com.example.braidw.entity.Sales;
import com.example.braidw.entity.ExpenseIncome;
import com.example.braidw.entity.CustomerVisit;
import com.example.braidw.repository.sales.SalesRepository;
import com.example.braidw.repository.sales.ExpenseIncomeRepository;
import com.example.braidw.repository.customer.CustomerVisitRepository;
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
    
    public SalesResponse getDashboardData() {
        Sales todaySales = salesRepository.findTodaySales();
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);
        
        return SalesResponse.builder()
                .summary(createSalesSummary(todaySales))
                .salesGraph(createSalesGraph(weekAgo))
                .expenseIncome(createExpenseIncome())
                .customerVisit(createCustomerVisit(weekAgo))
                .build();
    }
    
    private SalesResponse.SalesSummary createSalesSummary(Sales todaySales) {
        // 어제의 매출과 비교하여 증감 계산
        Sales yesterdaySales = salesRepository.findDailySales(
                LocalDateTime.now().minusDays(1).withHour(0).withMinute(0),
                LocalDateTime.now().minusDays(1).withHour(23).withMinute(59)
        ).get(0);
        
        long change = todaySales.getAmount() - yesterdaySales.getAmount();
        int percentage = (int) ((change * 100.0) / yesterdaySales.getAmount());
        
        return SalesResponse.SalesSummary.builder()
                .title("오늘 총 매출")
                .value(formatAmount(todaySales.getAmount()))
                .change(formatAmount(change))
                .percentage(Math.abs(percentage))
                .trend(change >= 0 ? "up" : "down")
                .build();
    }
    
    private SalesResponse.SalesGraph createSalesGraph(LocalDateTime startDate) {
        List<Sales> salesData = salesRepository.findDailySales(startDate, LocalDateTime.now());
        
        Map<String, List<String>> dates = new HashMap<>();
        Map<String, List<Long>> values = new HashMap<>();
        
        // 일별 데이터
        DateTimeFormatter dailyFormatter = DateTimeFormatter.ofPattern("M/d");
        dates.put("일별", salesData.stream()
                .map(s -> s.getDate().format(dailyFormatter))
                .collect(Collectors.toList()));
        values.put("일별", salesData.stream()
                .map(Sales::getAmount)
                .collect(Collectors.toList()));
        
        return SalesResponse.SalesGraph.builder()
                .dates(dates)
                .values(values)
                .build();
    }
    
    private SalesResponse.ExpenseIncome createExpenseIncome() {
        YearMonth currentMonth = YearMonth.now();
        ExpenseIncome data = expenseIncomeRepository.findByMonth(currentMonth)
                .orElseThrow(() -> new RuntimeException("Current month expense/income data not found"));
        
        // 전월 대비 비율 계산
        int expenseRatio = (int) ((data.getExpense() * 100.0) / data.getPreviousExpense());
        int incomeRatio = (int) ((data.getIncome() * 100.0) / data.getPreviousIncome());
        
        // 전월 대비 변화율 계산
        int expenseChange = expenseRatio - 100;
        int incomeChange = incomeRatio - 100;
        
        return SalesResponse.ExpenseIncome.builder()
                .expense(data.getExpense())
                .income(data.getIncome())
                .expenseRatio(expenseRatio)
                .incomeRatio(incomeRatio)
                .expenseChange(expenseChange)
                .incomeChange(incomeChange)
                .build();
    }
    
    private SalesResponse.CustomerVisit createCustomerVisit(LocalDateTime startDate) {
        List<CustomerVisit> visits = customerVisitRepository
                .findByVisitDateBetweenOrderByVisitDateAsc(startDate, LocalDateTime.now());
        
        return SalesResponse.CustomerVisit.builder()
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
    }
    
    private String formatAmount(long amount) {
        return String.format("%,d원", amount);
    }
} 