package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ExpenseData {
    private UUID expenseId;

    @NotNull
    private UUID category;

    private UUID mainCategory;

    private String localizedName;

    @NotNull
    @Positive(message = "AMOUNT_POSITIVE")
    private BigDecimal amount;

    @Size(max = 255, message = "DESCRIPTION_LENGTH")
    private String description;

    @NotNull
    private LocalDate paymentDate;
}