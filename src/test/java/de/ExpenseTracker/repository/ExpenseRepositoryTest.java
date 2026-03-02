package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Category;
import de.ExpenseTracker.model.Expense;
import de.ExpenseTracker.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpenseRepositoryTest {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Users savedUser;
    private Category savedCategory;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(createUser());
        savedCategory = categoryRepository.save(createCategory());
        expenseRepository.save(createExpense());
    }


    @Test
    void testShouldReturnAllExpensesByUserId() {
        List<Expense> expense = expenseRepository.findByUser_Userid(savedUser.getUserid());

        assertExpense(expense);
    }

    @Test
    void testShouldReturnExpensesByUserIdAndCategoryId() {
        List<Expense> expense = expenseRepository.findByUser_UseridAndCategory_CategoryId(savedUser.getUserid(), savedCategory.getCategoryId());

        assertExpense(expense);
    }

    // helper methods
    private Expense createExpense() {
        return Expense.builder()
                .expenseId(UUID.randomUUID())
                .description("This is a description")
                .amount(BigDecimal.valueOf(12.20))
                .createdAt(Instant.now())
                .user(savedUser)
                .category(savedCategory)
                .build();
    }

    private Users createUser() {
        return Users.builder()
                .userid(UUID.randomUUID())
                .username("TestUser")
                .passwordHash("PasswordHash")
                .lastLogin(null)
                .createdAt(Instant.now())
                .build();
    }

    private Category createCategory() {
        return Category.builder()
                .categoryId(UUID.randomUUID())
                .name("Category")
                .parent(null)
                .build();
    }

    private void assertExpense(List<Expense> expense) {
        assertThat(expense).hasSize(1);
        assertThat(expense.getFirst().getDescription()).isEqualTo("This is a description");
        assertThat(expense.getFirst().getAmount()).isEqualTo(BigDecimal.valueOf(12.20));
        assertThat(expense.getFirst().getCreatedAt()).isNotNull();
        assertThat(expense.getFirst().getUser().getUserid()).isEqualTo(savedUser.getUserid());
        assertThat(expense.getFirst().getCategory().getCategoryId()).isEqualTo(savedCategory.getCategoryId());
    }
}
