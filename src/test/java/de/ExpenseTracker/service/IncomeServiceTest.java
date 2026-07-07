package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.IncomeData;
import de.ExpenseTracker.mapper.IncomeMapper;
import de.ExpenseTracker.model.Income;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.IncomeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.ExpenseTracker.TestDataFactory.createIncomeData;
import static org.assertj.core.api.Assertions.assertThat;
import static de.ExpenseTracker.TestDataFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private Users user;
    private Income income;


    @BeforeEach
    void setUp() {
        user = createUser();

        income = Income.builder()
                .incomeId(UUID.randomUUID())
                .title("Income Title")
                .amount(BigDecimal.valueOf(60))
                .incomeDate(LocalDate.now())
                .user(user)
                .build();
    }

    // positive
    @Test
    void shouldCreateNewIncome() {
        when(userService.getCurrentUserFromSession())
                .thenReturn(createUser());

        when(incomeRepository.save(any(Income.class)))
                .thenAnswer(inv -> inv.getArgument(0));

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

    @Test
    void shouldReturnUserIncomes() {
        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(incomeRepository.findByUser_Userid(user.getUserid()))
                .thenReturn(List.of(income));

        when(incomeMapper.mapToDto(any(Income.class)))
                .thenAnswer(inv -> {
                    Income i = inv.getArgument(0);

                    return IncomeData.builder()
                            .title(i.getTitle())
                            .amount(i.getAmount())
                            .incomeDate(i.getIncomeDate())
                            .build();
                });
        List<IncomeData> result = incomeService.getIncomesForCurrentUser();
        IncomeData dto = result.getFirst();
        assertEquals(income.getAmount(), dto.getAmount());
        assertEquals(income.getTitle(), dto.getTitle());
        assertEquals(income.getIncomeDate(), dto.getIncomeDate());
    }

    @Test
    void shouldDeleteIncome() {
        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        doNothing().when(incomeRepository)
                .deleteByUser_UseridAndIncomeId(user.getUserid(), income.getIncomeId());

        incomeService.deleteIncome(income.getIncomeId());

        verify(incomeRepository)
                .deleteByUser_UseridAndIncomeId(user.getUserid(), income.getIncomeId());
    }

    @Test
    void shouldUpdateIncome() {
        IncomeData updateDto = IncomeData.builder()
                .title("New Title")
                .amount(BigDecimal.valueOf(99.99))
                .incomeDate(LocalDate.now().plusDays(1))
                .build();

        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(incomeRepository.findByUser_UseridAndIncomeId(user.getUserid(), income.getIncomeId()))
                .thenReturn(Optional.of(income));

        incomeService.updateIncome(income.getIncomeId(), updateDto);

        assertEquals(updateDto.getTitle(), income.getTitle());
        assertEquals(updateDto.getAmount(), income.getAmount());
        assertEquals(updateDto.getIncomeDate(), income.getIncomeDate());
    }

    @Test
    void shouldReturnIncomeDetails() {
        Income income = Income.builder()
                .incomeId(UUID.randomUUID())
                .title("Test")
                .amount(BigDecimal.TEN)
                .incomeDate(LocalDate.now())
                .user(user)
                .build();

        IncomeData incomeData = IncomeData.builder()
                .incomeId(income.getIncomeId())
                .title(income.getTitle())
                .amount(income.getAmount())
                .incomeDate(income.getIncomeDate())
                .build();


        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(incomeRepository.findByUser_UseridAndIncomeId(user.getUserid(), income.getIncomeId()))
                .thenReturn(Optional.of(income));

        when(incomeMapper.mapToDto(income))
                .thenReturn(incomeData);

        IncomeData result = incomeService.getIncomeDetails(income.getIncomeId());

        assertEquals(incomeData.getIncomeId(), result.getIncomeId());
        assertEquals(incomeData.getTitle(), result.getTitle());
        assertEquals(incomeData.getAmount(), result.getAmount());
        assertEquals(incomeData.getIncomeDate(), result.getIncomeDate());
    }
}