package de.ExpenseTracker.repository;

import de.ExpenseTracker.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Note:
I intentionally did not test for extreme edge cases, like overly long passwords ord usernames.
This and the following tests focus on the most important repository features, primarily to help me learn and write
cleaner tests.
Duplicate usernames will be handled at the service layer.
As I progress I will come back to this project and use this as a further learning project.
 */
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Small helper method to reduce redundant code for creating the users.
     * @return Users - The full User example for registration / login
     */
    private Users createTestUser() {
        return Users.builder()
                .userid(UUID.randomUUID())
                .username("testuser")
                .passwordHash("hashpwd")
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void testCreateUser() {
        Users saved = userRepository.save(createTestUser());

        assertThat(saved).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUserid()).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("testuser");
        assertThat(saved.getPasswordHash()).isEqualTo("hashpwd");

        Users found = userRepository.findById(saved.getUserid()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
    }

    @Test
    void testFindByUsername() {
        Users saved = userRepository.save(createTestUser());
        assertThat(saved).isNotNull();

        Users found = userRepository.findByUsername(saved.getUsername()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
    }

    @Test
    void testExistsByUserName() {
        Users saved = userRepository.save(createTestUser());
        assertThat(saved).isNotNull();

        assertTrue(userRepository.existsByUsername(saved.getUsername()));
    }

    @Test
    void testFindByUsername_NotFound() {
        assertThat(userRepository.findByUsername("doesNotExist")).isEmpty();
    }
}