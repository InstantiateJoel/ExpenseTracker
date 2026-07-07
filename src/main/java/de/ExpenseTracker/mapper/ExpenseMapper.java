package de.ExpenseTracker.mapper;

import de.ExpenseTracker.dto.CategoryData;
import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExpenseMapper {

    private final CategoryService categoryService;

    public ExpenseData mapToDto(Expense expense) {
        Category sub = expense.getCategory();
        Category main = sub.getParent();

        CategoryData category = categoryService.getLocalizedCategory(expense.getCategory().getCategoryId());

        return ExpenseData.builder()
                .expenseId(expense.getExpenseId())
                .category(category.getCategoryId())
                .localizedName(category.getLocalizedName())
                .mainCategory(main.getCategoryId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .paymentDate(expense.getPaymentDate())
                .build();
    }
}