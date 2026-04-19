package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.mapper.IncomeMapper;
import de.ExpenseTracker.model.Income;
import de.ExpenseTracker.repository.IncomeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static de.ExpenseTracker.TestDataFactory.createIncomeData;
import static org.assertj.core.api.Assertions.assertThat;
import static de.ExpenseTracker.TestDataFactory.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {
    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private UserService userService;

    @Mock
    private IncomeMapper incomeMapper;

    @InjectMocks
    private IncomeService incomeService;

    // positive
    @Test
    void shouldCreateNewIncome() {
        when(userService.getCurrentUserFromSession())
                .thenReturn(createUser());

        when(incomeRepository.save(any(Income.class)))
                .thenAnswer(inv ->  inv.getArgument(0));

        when(incomeMapper.mapToDto(any(Income.class)))
                .thenAnswer(inv -> {
                    Income income = inv.getArgument(0);

                    return IncomeData.builder()
                            .title(income.getTitle())
                            .amount(income.getAmount())
                            .incomeDate(income.getIncomeDate())
                            .build();
                });

        IncomeData input = createIncomeData();

        IncomeData result = incomeService.createNewIncome(input);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(input.getTitle());
        assertThat(result.getAmount()).isEqualTo(input.getAmount());
        assertThat(result.getIncomeDate());
    }
}