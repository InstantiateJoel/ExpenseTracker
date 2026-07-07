package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class IncomeData {
    private UUID incomeId;
    @NotNull
    private String title;

    @Positive(message = "AMOUNT_POSITIVE")
    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate incomeDate;
}