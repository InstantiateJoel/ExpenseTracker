package de.ExpenseTracker;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Users;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class TestDataFactory {

    public static Users createUser() {
        return Users.builder()
                .userid(UUID.randomUUID())
                .username("TestUser")
                .passwordHash("PaswordHash")
                .createdAt(Instant.now())
                .build();
    }

    public static Category createCategory() {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name("Category")
                .build();
    }

    public static ExpenseData createExpenseData() {
        return ExpenseData.builder()
                .category(UUID.randomUUID())
                .amount(BigDecimal.valueOf(12.40))
                .description("description")
                .paymentDate(LocalDate.now())
                .build();
    }

    public static IncomeData createIncomeData() {
        return IncomeData.builder()
                .title("IncomeTitle")
                .amount(BigDecimal.valueOf(20.20))
                .incomeDate(LocalDate.now())
                .build();
    }
}
