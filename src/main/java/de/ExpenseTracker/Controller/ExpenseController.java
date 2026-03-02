package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    /**
     * Creates a new Expense for a given user
     *
     * @param expenseData DTO containing category, amount, description
     * @return returns the Expense DTO
     */
    @PostMapping
    public ExpenseData createExpense(@Valid @RequestBody ExpenseData expenseData) {
        return expenseService.createNewExpense(expenseData);
    }

    /**
     * Retrieves all expenses for a given user
     *
     * @return List of Expense DTOs for all expenses created by this user
     */
    @GetMapping
    public List<ExpenseData> getUserExpenses() {
        return expenseService.getExpensesForCurrentUser();
    }
}