package de.ExpenseTracker.mapper;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.model.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public ExpenseData mapToDto(Expense expense) {
        return ExpenseData.builder()
                .category(expense.getCategory().getCategoryId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .paymentDate(expense.getPaymentDate())
                .build();
    }
}
