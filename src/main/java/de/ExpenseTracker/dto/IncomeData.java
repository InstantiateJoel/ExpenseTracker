package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class IncomeData {
    @NotNull
    private String title;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate incomeDate;
}