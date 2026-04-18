package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.mapper.IncomeMapper;
import de.ExpenseTracker.model.Income;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final UserService userService;
    private final IncomeMapper incomeMapper;

    /**
     * Creates a new Expense for a given user and persists it in the database
     *
     * @param incomeData The Dto containing income details (title, amount, incomeDate)
     * @return IncomeData DTO representing teh saved income, including its title, amount and incomeDate
     */
    public IncomeData createNewIncome(IncomeData incomeData) {
        Users user = userService.getCurrentUserFromSession();

        Income income = Income.builder()
                .incomeId(UUID.randomUUID())
                .title(incomeData.getTitle())
                .amount(incomeData.getAmount())
                .incomeDate(incomeData.getIncomeDate())
                .user(user)
                .build();

        Income saved = incomeRepository.save(income);

        return incomeMapper.mapToDto(saved);
    }

    /**
     * Retrieves all incomes for a given user
     *
     * @return List of IncomeData DTOs for alle incomes created by this user
     */
    public List<IncomeData> getIncomesForCurrentUser() {
        Users user = userService.getCurrentUserFromSession();

        return incomeRepository.findByUser_Userid(user.getUserid())
                .stream()
                .map(incomeMapper::mapToDto)
                .toList();
    }
}