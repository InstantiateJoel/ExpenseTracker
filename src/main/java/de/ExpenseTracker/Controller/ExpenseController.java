package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;


    @PostMapping
    public ExpenseData createExpense(@Valid @RequestBody ExpenseData expenseData) {
        return expenseService.createNewExpense(expenseData);
    }

    @GetMapping
    public List<ExpenseData> getUserExpenses() {
        return expenseService.getExpensesForCurrentUser();
    }

    @DeleteMapping ("/{expenseId}")
    public void deleteExpense(@PathVariable UUID expenseId) {
        expenseService.deleteExpense(expenseId);
    }

    @PatchMapping("/{expenseId}")
    public void updateExpense(@PathVariable UUID expenseId, @RequestBody ExpenseData expenseData) {
        expenseService.updateExpense(expenseId, expenseData);
    }

    @GetMapping("/{expenseId}")
    public ExpenseData getExpenseDetails(@PathVariable UUID expenseId) {
        return expenseService.getExpenseDetails(expenseId);
    }
}