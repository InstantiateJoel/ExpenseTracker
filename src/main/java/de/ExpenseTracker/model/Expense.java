package de.ExpenseTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expense")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @Column(name = "expenseid", updatable = false, nullable = false)
    private UUID expenseId;

    @Column(name = "description")
    private String description;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}