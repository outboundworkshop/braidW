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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private YearMonth month;  // 해당 월

    private Long expense;     // 지출액
    private Long income;      // 수입액
    
    @Column(name = "previous_expense")
    private Long previousExpense;  // 전월 지출액
    
    @Column(name = "previous_income")
    private Long previousIncome;   // 전월 수입액

    @Transient
    public int getExpenseRatio() {
        if (expense == null || previousExpense == null || previousExpense == 0L) {
            return 0; // 또는 적절한 기본값 또는 예외 처리
        }
        return (int) ((expense * 100.0) / previousExpense);
    }
    
    @Transient
    public int getIncomeRatio() {
        if (income == null || previousIncome == null || previousIncome == 0L) {
            return 0; // 또는 적절한 기본값 또는 예외 처리
        }
        return (int) ((income * 100.0) / previousIncome);
    }
    
    @Transient
    public int getExpenseChange() {
        return getExpenseRatio() - 100;
    }
    
    @Transient
    public int getIncomeChange() {
        return getIncomeRatio() - 100;
    }
} 