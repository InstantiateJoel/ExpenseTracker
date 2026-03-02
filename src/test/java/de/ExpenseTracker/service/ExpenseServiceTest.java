package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.ExpenseData;
import de.ExpenseTracker.exceptions.CategoryNotFoundException;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    CategoryService categoryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ExpenseService expenseService;

    // create
    // positive
    @Test
    void shouldCreateNewExpense() {
        Category category = createCategory();

        when(categoryService.getCategoryByIdOrThrow(category.getCategoryId()))
                .thenReturn(category);

        when(userService.getCurrentUserFromSession())
                .thenReturn(createUser());

        when(expenseRepository.save(any(Expense.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

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
        Users user = createUser();
        Expense expense1 = createExpense();
        Expense expense2 = createExpense();

        when(userService.getCurrentUserFromSession())
                .thenReturn(user);

        when(expenseRepository.findByUser_Userid(user.getUserid()))
                .thenReturn(List.of(expense1, expense2));

        List<ExpenseData> result = expenseService.getExpensesForCurrentUser();

        assertEquals(2, result.size());
        assertEquals(expense1.getDescription(), result.get(0).getDescription());
        assertEquals(expense1.getCategory().getCategoryId(), result.get(0).getCategory());
        assertEquals(expense2.getDescription(), result.get(1).getDescription());
        assertEquals(expense2.getCategory().getCategoryId(), result.get(1).getCategory());

    }

    // helper methods
    private Expense createExpense() {
        return Expense.builder()
                .expenseId(UUID.randomUUID())
                .category(createCategory())
                .description("This is a description")
                .createdAt(Instant.now())
                .user(createUser())
                .build();
    }

    public Category createCategory() {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name("Category")
                .build();
    }

    private ExpenseData createExpenseDto(UUID categoryId) {
        return ExpenseData.builder()
                .category(categoryId)
                .amount(BigDecimal.valueOf(42.0))
                .description("Test expense")
                .build();
    }

    private Users createUser()  {
        return Users.builder()
                .userid(UUID.randomUUID())
                .username("joel")
                .createdAt(Instant.now())
                .passwordHash("hashedPassword")
                .build();
    }
}
