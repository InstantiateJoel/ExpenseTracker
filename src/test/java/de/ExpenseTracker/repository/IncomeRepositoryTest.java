package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Income;
import de.ExpenseTracker.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class IncomeRepositoryTest {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    private Users savedUser;


    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(createUser());
        incomeRepository.save(createIncome());
    }

    @Test
    void testShouldReturnAllIncomeByUserId() {
        List<Income> income = incomeRepository.findByUser_Userid(savedUser.getUserid());

        assertIncome(income);
    }

    // helper methods
    private Users createUser() {
        return Users.builder()
                .userid(UUID.randomUUID())
                .username("TestUser")
                .passwordHash("PasswordHash")
                .lastLogin(null)
                .createdAt(Instant.now())
                .build();
    }

    private Income createIncome() {
        return Income.builder()
                .incomeId(UUID.randomUUID())
                .title("IncomeTitle")
                .amount(BigDecimal.valueOf(20.20))
                .incomeDate(LocalDate.now())
                .user(savedUser)
                .build();
    }

    private void assertIncome(List<Income> income) {
        assertThat(income).hasSize(1);
        assertThat(income.getFirst().getTitle()).isEqualTo("IncomeTitle");
        assertThat(income.getFirst().getAmount()).isEqualTo(BigDecimal.valueOf(20.20));
        assertThat(income.getFirst().getIncomeDate()).isNotNull();
        assertThat(income.getFirst().getUser().getUserid()).isEqualTo(savedUser.getUserid());
    }
}
