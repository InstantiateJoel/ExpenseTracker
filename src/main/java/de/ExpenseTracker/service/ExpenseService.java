package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    /**
     * Creates a new Expense for a given user and persists it in the database
     *
     * @param expenseData The DTO containing expense details (category, description, amount)
     * @return ExpenseData DTO representing the saved expense, including its category ID, description, and amount
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

        return mapToDto(saved);
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
                .map(this::mapToDto)
                .toList();

    }

    /**
     * Maps the Expense entity into a DTO
     *
     * @param expense the expense to map
     * @return The Expense DTO
     */
    private ExpenseData mapToDto(Expense expense) {
        return ExpenseData.builder()
                .category(expense.getCategory().getCategoryId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }
}