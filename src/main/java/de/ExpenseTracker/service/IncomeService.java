package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.exceptions.IncomeNotFoundException;
import de.ExpenseTracker.mapper.IncomeMapper;
import de.ExpenseTracker.model.Income;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.IncomeRepository;
import jakarta.transaction.Transactional;
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

    /**
     * Deletes a user income by incomeId
     *
     * @param incomeId - The ID of the income to delete
     */
    @Transactional
    public void deleteIncome(UUID incomeId) {
        Users user = userService.getCurrentUserFromSession();

        incomeRepository.deleteByUser_UseridAndIncomeId(user.getUserid(), incomeId);
    }

    /**
     * Updates a user income by Income ID
     *
     * @param incomeId   - The ID of the income to update
     * @param incomeData - The income data containing the fields to update
     */
    @Transactional
    public void updateIncome(UUID incomeId, IncomeData incomeData) {
        Users user = userService.getCurrentUserFromSession();

        Income income = incomeRepository.findByUser_UseridAndIncomeId(user.getUserid(), incomeId)
                .orElseThrow(() -> new IncomeNotFoundException(ErrorCode.INCOME_NOT_FOUND));

        if (incomeData.getTitle() != null) {
            income.setTitle(incomeData.getTitle());
        }
        if (incomeData.getAmount() != null) {
            income.setAmount(incomeData.getAmount());
        }
        if (incomeData.getIncomeDate() != null) {
            income.setIncomeDate(incomeData.getIncomeDate());
        }
    }

    /**
     * Retrieves the details of a specific income belonging to the current user
     *
     * @param incomeId - The ID of the income to retrieve
     * @return - the income details
     * @throws IncomeNotFoundException - If no income with the given ID exists for the current user
     */
    public IncomeData getIncomeDetails(UUID incomeId) {
        Users user = userService.getCurrentUserFromSession();

        Income income = incomeRepository.findByUser_UseridAndIncomeId(user.getUserid(), incomeId)
                .orElseThrow(() -> new IncomeNotFoundException(ErrorCode.INCOME_NOT_FOUND));


        return incomeMapper.mapToDto(income);
    }
}