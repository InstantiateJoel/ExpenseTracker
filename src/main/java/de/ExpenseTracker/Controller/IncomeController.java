package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.service.IncomeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @DeleteMapping("/{incomeId}")
    public void deleteIncome(@PathVariable UUID incomeId) {
        incomeService.deleteIncome(incomeId);
    }

    @PatchMapping("/{incomeId}")
    public void updateIncome(@PathVariable UUID incomeId, @RequestBody IncomeData incomeData) {
        incomeService.updateIncome(incomeId, incomeData);
    }

    @GetMapping("/{incomeId}")
    public IncomeData getIncomeDetails(@PathVariable UUID incomeId) {
        return incomeService.getIncomeDetails(incomeId);
    }
}
