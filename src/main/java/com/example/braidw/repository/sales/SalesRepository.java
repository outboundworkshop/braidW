package com.example.braidw.repository.sales;

import com.example.braidw.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    // 일별 매출 조회
    @Query("SELECT s FROM Sales s WHERE s.date BETWEEN :startDate AND :endDate ORDER BY s.date")
    List<Sales> findDailySales(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 오늘의 매출 조회
    @Query("SELECT s FROM Sales s WHERE DATE(s.date) = CURRENT_DATE")
    Sales findTodaySales();
    
    // 특정 기간의 총 매출액 조회
    @Query("SELECT SUM(s.amount) FROM Sales s WHERE s.date BETWEEN :startDate AND :endDate")
    Long calculateTotalSales(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 특정 기간의 평균 전환율 조회
    @Query("SELECT AVG(s.conversionRate) FROM Sales s WHERE s.date BETWEEN :startDate AND :endDate")
    Double calculateAverageConversionRate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
} 