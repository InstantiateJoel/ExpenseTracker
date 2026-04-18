package de.ExpenseTracker.mapper;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.model.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {
    public IncomeData mapToDto(Income income) {
        return IncomeData.builder()
                .title(income.getTitle())
                .amount(income.getAmount())
                .incomeDate(income.getIncomeDate())
                .build();
    }
}
