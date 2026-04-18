package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.service.IncomeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income")
@AllArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping
    public IncomeData createIncome(@Valid @RequestBody IncomeData incomeData) {
        return incomeService.createNewIncome(incomeData);
    }

    @GetMapping
    public List<IncomeData> getUserIncomes() {
        return incomeService.getIncomesForCurrentUser();
    }
}
