package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.mapper.ExpenseMapper;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ExpenseMapper expenseMapper;

    /**
     * Creates a new Expense for a given user and persists it in the database
     *
     * @param expenseData The DTO containing expense details (category, description, amount, paymentDate)
     * @return ExpenseData DTO representing the saved expense, including its category ID, description, amount and paymentDate
     */
    public ExpenseData createNewExpense(ExpenseData expenseData) {
        Users user = userService.getCurrentUserFromSession();

        Category category = categoryService.getCategoryByIdOrThrow(expenseData.getCategory());

        Expense expense = Expense.builder()
                .expenseId(UUID.randomUUID())
                .description(expenseData.getDescription())
                .category(category)
                .amount(expenseData.getAmount())
                .user(user)
                .paymentDate(expenseData.getPaymentDate())
                .build();

        Expense saved = expenseRepository.save(expense);

        return expenseMapper.mapToDto(saved);
    }

    /**
     * Retrieves all expenses for a given user
     *
     * @return List of ExpenseData DTOs for all expenses created by this user
     */
    public List<ExpenseData> getExpensesForCurrentUser() {
        Users user = userService.getCurrentUserFromSession();

        return expenseRepository.findByUser_Userid(user.getUserid())
                .stream()
                .map(expenseMapper::mapToDto)
                .toList();
    }
}