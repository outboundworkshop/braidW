package com.example.braidw.repository.sales;

import com.example.braidw.entity.ExpenseIncome;
import com.example.braidw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.YearMonth;
import java.util.Optional;

public interface ExpenseIncomeRepository extends JpaRepository<ExpenseIncome, Long> {
    Optional<ExpenseIncome> findByMonth(YearMonth month);

    @Query("SELECT ei FROM ExpenseIncome ei WHERE ei.month = :month AND ei.user = :user")
    Optional<ExpenseIncome> findByMonthAndUser(@Param("month") YearMonth month, @Param("user") User user);
} 