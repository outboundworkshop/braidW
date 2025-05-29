package com.example.braidw.repository.customer;

import com.example.braidw.entity.CustomerVisit;
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
    
    @Query("SELECT DISTINCT ct FROM CustomerVisit cv JOIN cv.customerTags ct")
    List<String> findAllCustomerTags();
} 