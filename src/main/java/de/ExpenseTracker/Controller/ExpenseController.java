package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.ExpenseData;
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


    // Retrieves all user expenses
    @GetMapping("/all")
    public List<ExpenseData> getUserExpenses() {
        return expenseService.getExpensesForCurrentUser();
    }

    // retrieves the first user expense
    @GetMapping("/first") // todo: need to add errors!!
    public ExpenseData getFirstUserExpenses() {
        return expenseService.getFirstUserExpense();
    }

    // retrieves all the information for an expense, to send it to the frontend for editing
    @GetMapping("/{expenseId}")
    public ExpenseData getExpenseDetails(@PathVariable UUID expenseId) {
        return expenseService.getExpenseDetails(expenseId);
    }

    @GetMapping("/by-year-month")
    public List<ExpenseData> getExpensesByMonthYear(@RequestParam int year, @RequestParam int month) {
        return expenseService.getExpensesForCurrentUserByMonthAndYear(year, month);
    }

    // adds a new expense
    @PostMapping
    public ExpenseData createExpense(@Valid @RequestBody ExpenseData expenseData) {
        return expenseService.createNewExpense(expenseData);
    }

    // Deletes an expense
    @DeleteMapping ("/{expenseId}")
    public void deleteExpense(@PathVariable UUID expenseId) {
        expenseService.deleteExpense(expenseId);
    }

    // edits an expense
    @PatchMapping("/{expenseId}")
    public void updateExpense(@PathVariable UUID expenseId, @RequestBody ExpenseData expenseData) {
        expenseService.updateExpense(expenseId, expenseData);
    }
}