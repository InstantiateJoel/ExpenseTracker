package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.exceptions.CategoryNotFoundException;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.mapper.ExpenseMapper;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.ExpenseRepository;
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

import static de.ExpenseTracker.TestDataFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    CategoryService categoryService;

    @Mock
    private UserService userService;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseService expenseService;

    private Users user;
    private Category category;
    private Expense expense;

    @BeforeEach
    void setUp() {
        user = createUser();
        category = createCategory();

        expense = Expense.builder()
            .expenseId(UUID.randomUUID())
            .category(category)
            .description("This is a description")
            .paymentDate(LocalDate.now())
            .user(user)
            .build();
    }

    // create
    // positive
    @Test
    void shouldCreateNewExpense() {
        when(categoryService.getCategoryByIdOrThrow(category.getCategoryId()))
                .thenReturn(category);

        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(expenseRepository.save(any(Expense.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        when(expenseMapper.mapToDto(any(Expense.class)))
                .thenAnswer(inv -> {
                    Expense e = inv.getArgument(0);

                    return ExpenseData.builder()
                            .category(e.getCategory().getCategoryId())
                            .description(e.getDescription())
                            .amount(e.getAmount())
                            .paymentDate(e.getPaymentDate())
                            .build();
                });

        ExpenseData result = expenseService.createNewExpense(createExpenseDto(category.getCategoryId()));

        // verify that all fields are correct
        assertEquals(category.getCategoryId(), result.getCategory());
        assertEquals("Test expense", result.getDescription());
        assertEquals(BigDecimal.valueOf(42.0), result.getAmount());
    }

    // negative
    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        UUID categoryId = UUID.randomUUID();

        when(categoryService.getCategoryByIdOrThrow(categoryId))
                .thenThrow(new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        assertThrows(CategoryNotFoundException.class, () -> expenseService.createNewExpense(createExpenseDto(categoryId)));
    }

    // get expenses by userid
    @Test
    void shouldReturnAllUserExpenses() {
        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(expenseRepository.findByUser_Userid(user.getUserid()))
                .thenReturn(List.of(expense));

        when(expenseMapper.mapToDto(any(Expense.class)))
                .thenAnswer(inv -> {
                    Expense e = inv.getArgument(0);

                    return ExpenseData.builder()
                            .category(e.getCategory().getCategoryId())
                            .description(e.getDescription())
                            .amount(e.getAmount())
                            .paymentDate(e.getPaymentDate())
                            .build();
                });
        List<ExpenseData> result = expenseService.getExpensesForCurrentUser();

        assertEquals(1, result.size());
        ExpenseData dto = result.getFirst();

        assertEquals(expense.getDescription(), dto.getDescription());
        assertEquals(expense.getCategory().getCategoryId(), dto.getCategory());
    }

    @Test
    void shouldDeleteExpense() {
        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        doNothing().when(expenseRepository)
                .deleteByUser_UseridAndExpenseId(user.getUserid(), expense.getExpenseId());

        expenseService.deleteExpense(expense.getExpenseId());

        verify(expenseRepository)
                .deleteByUser_UseridAndExpenseId(user.getUserid(), expense.getExpenseId());
    }

    @Test
    void shouldUpdateExpense() {
        Category category = createCategory();

        ExpenseData updateDto = ExpenseData.builder()
                .category(category.getCategoryId())
                .amount(BigDecimal.valueOf(99.99))
                .description("Updated description")
                .paymentDate(LocalDate.now().plusDays(1))
                .build();

        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(expenseRepository.findByUser_UseridAndExpenseId(user.getUserid(), expense.getExpenseId()))
                .thenReturn(Optional.of(expense));

        when(categoryService.getCategoryByIdOrThrow(category.getCategoryId()))
                .thenReturn(category);

        expenseService.updateExpense(expense.getExpenseId(), updateDto);

        assertEquals(category, expense.getCategory());
        assertEquals(BigDecimal.valueOf(99.99), expense.getAmount());
        assertEquals("Updated description", expense.getDescription());
        assertEquals(updateDto.getPaymentDate(), expense.getPaymentDate());

        verify(expenseRepository)
                .findByUser_UseridAndExpenseId(user.getUserid(), expense.getExpenseId());
    }

    @Test
    void shouldReturnExpenseDetails() {
        Expense expense = Expense.builder()
                .expenseId(UUID.randomUUID())
                .category(createCategory())
                .description("Description")
                .paymentDate(LocalDate.now())
                .build();

        ExpenseData expenseData = ExpenseData.builder()
                .expenseId(expense.getExpenseId())
                .category(expense.getCategory().getCategoryId())
                .description(expense.getDescription())
                .paymentDate(expense.getPaymentDate())
                .build();

        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(expenseRepository.findByUser_UseridAndExpenseId(user.getUserid(), expense.getExpenseId()))
                .thenReturn(Optional.of(expense));

        when(expenseMapper.mapToDto(expense))
                .thenReturn(expenseData);

        ExpenseData result = expenseService.getExpenseDetails(expense.getExpenseId());

        assertEquals(expenseData.getExpenseId(), result.getExpenseId());
        assertEquals(expenseData.getCategory(), result.getCategory());
        assertEquals(expenseData.getAmount(), result.getAmount());
        assertEquals(expense.getDescription(), result.getDescription());
        assertEquals(expenseData.getPaymentDate(), result.getPaymentDate());
    }

    // helper methods
    private ExpenseData createExpenseDto(UUID categoryId) {
        return ExpenseData.builder()
                .category(categoryId)
                .amount(BigDecimal.valueOf(42.0))
                .description("Test expense")
                .build();
    }

    private Category createCategory() {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name("Category")
                .build();
    }
}