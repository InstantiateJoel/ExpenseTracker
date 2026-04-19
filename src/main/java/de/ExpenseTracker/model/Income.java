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
@Table (name = "income")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {
    @Id
    @Column (name = "incomeid", updatable = false, nullable = false)
    private UUID incomeId;

    @Column (name = "title")
    private String title;

    @Column (name = "amount")
    private BigDecimal amount;

    @Column (name = "income_date")
    private LocalDate incomeDate;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private Users user;
}
