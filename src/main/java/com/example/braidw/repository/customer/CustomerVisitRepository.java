package com.example.braidw.repository.customer;

import com.example.braidw.entity.CustomerVisit;
import com.example.braidw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerVisitRepository extends JpaRepository<CustomerVisit, Long> {
    List<CustomerVisit> findByVisitDateBetweenOrderByVisitDateAsc(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT SUM(cv.newCustomerCount) FROM CustomerVisit cv WHERE cv.visitDate BETWEEN :startDate AND :endDate")
    Integer sumNewCustomers(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(cv.returningCustomerCount) FROM CustomerVisit cv WHERE cv.visitDate BETWEEN :startDate AND :endDate")
    Integer sumReturningCustomers(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT cv FROM CustomerVisit cv WHERE cv.user = :user AND cv.visitDate BETWEEN :startDate AND :endDate ORDER BY cv.visitDate ASC")
    List<CustomerVisit> findByVisitDateBetweenAndUserOrderByVisitDateAsc(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT DISTINCT t FROM CustomerVisit cv JOIN cv.customerTags t WHERE cv.user = :user AND t IS NOT NULL AND t <> ''")
    List<String> findAllCustomerTags(@Param("user") User user);
} 