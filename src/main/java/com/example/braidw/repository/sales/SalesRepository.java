package com.example.braidw.repository.sales;

import com.example.braidw.entity.Sales;
import com.example.braidw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    // 일별 매출 조회
    @Query("SELECT s FROM Sales s WHERE s.user = :user AND s.date BETWEEN :startDate AND :endDate ORDER BY s.date")
    List<Sales> findDailySalesByUser(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 오늘의 매출 조회
    @Query("SELECT s FROM Sales s WHERE s.user = :user AND DATE(s.date) = CURRENT_DATE")
    Sales findTodaySalesByUser(@Param("user") User user);
    
    // 특정 기간의 총 매출액 조회
    @Query("SELECT SUM(s.amount) FROM Sales s WHERE s.user = :user AND s.date BETWEEN :startDate AND :endDate")
    Long calculateTotalSalesByUser(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 특정 기간의 평균 전환율 조회
    @Query("SELECT AVG(s.conversionRate) FROM Sales s WHERE s.user = :user AND s.date BETWEEN :startDate AND :endDate")
    Double calculateAverageConversionRateByUser(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
} 