package com.example.braidw.repository.sales;

import com.example.braidw.entity.ExpenseIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.YearMonth;
import java.util.Optional;

public interface ExpenseIncomeRepository extends JpaRepository<ExpenseIncome, Long> {
    Optional<ExpenseIncome> findByMonth(YearMonth month);
} 