package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class IncomeData {
    @NotNull
    private String title;
    private BigDecimal amount;
    private LocalDate incomeDate;
}