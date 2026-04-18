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
    @NotNull
    private UUID category;

    @NotNull
    @Positive
    private BigDecimal amount;

    @Size(max = 255)
    private String description;

    @NotNull
    private LocalDate paymentDate;
}