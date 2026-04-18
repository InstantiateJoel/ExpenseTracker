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


    @PostMapping
    public ExpenseData createExpense(@Valid @RequestBody ExpenseData expenseData) {
        return expenseService.createNewExpense(expenseData);
    }

    @GetMapping
    public List<ExpenseData> getUserExpenses() {
        return expenseService.getExpensesForCurrentUser();
    }
}