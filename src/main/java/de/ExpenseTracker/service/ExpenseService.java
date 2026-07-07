package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.exceptions.ExpenseNotFoundException;
import de.ExpenseTracker.exceptions.IncomeNotFoundException;
import de.ExpenseTracker.mapper.ExpenseMapper;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
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

    /**
     * Deletes a user expense by expenseId
     * @param expenseId - The ID of the expense to delete
     */
    @Transactional
    public void deleteExpense(UUID expenseId) {
        Users user = userService.getCurrentUserFromSession();

        expenseRepository.deleteByUser_UseridAndExpenseId(user.getUserid(), expenseId);
    }

    /**
     * Updates a user expense by Expense ID
     * @param expenseId - The ID of the expense to update
     * @param expenseData - The expense data containing the fields to update
     */
    @Transactional
    public void updateExpense(UUID expenseId, ExpenseData expenseData) {
        Users user = userService.getCurrentUserFromSession();

        Expense expense = expenseRepository
                .findByUser_UseridAndExpenseId(user.getUserid(), expenseId)
                    .orElseThrow(() -> new ExpenseNotFoundException(ErrorCode.EXPENSE_NOT_FOUND));

        if (expenseData.getCategory() != null) {
            expense.setCategory(categoryService.getCategoryByIdOrThrow(expenseData.getCategory()));
        }
        if(expenseData.getAmount() != null) {
            expense.setAmount(expenseData.getAmount());
        }
        if(expenseData.getPaymentDate() != null) {
            expense.setPaymentDate(expenseData.getPaymentDate());
        }
        if(expenseData.getDescription() != null) {
            expense.setDescription(expenseData.getDescription());
        }
    }

    /**
     * Retrieves the details of a specific expense belonging to the current user
     *
     * @param expenseId - The ID of the expense to retrieve
     * @return - The expense details
     * @throws ExpenseNotFoundException - If no expense with the given ID exists for the current user
     */
    public ExpenseData getExpenseDetails(UUID expenseId) {
        Users user = userService.getCurrentUserFromSession();

        Expense expense = expenseRepository.findByUser_UseridAndExpenseId(user.getUserid(), expenseId)
                .orElseThrow(() -> new IncomeNotFoundException(ErrorCode.EXPENSE_NOT_FOUND));

        return expenseMapper.mapToDto(expense);
    }
}