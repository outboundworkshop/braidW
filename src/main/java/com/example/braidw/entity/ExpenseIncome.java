package com.example.braidw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.YearMonth;

@Entity
@Table(name = "expense_income")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private YearMonth month;  // 해당 월

    private Long expense;     // 지출액
    private Long income;      // 수입액
    
    @Column(name = "previous_expense")
    private Long previousExpense;  // 전월 지출액
    
    @Column(name = "previous_income")
    private Long previousIncome;   // 전월 수입액
} 