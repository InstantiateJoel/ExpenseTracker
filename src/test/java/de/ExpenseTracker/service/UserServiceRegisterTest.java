package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.RegisterUserData;
import de.ExpenseTracker.exceptions.UserAlreadyExistsException;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceRegisterTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private AutoCloseable closeable;

    private final RegisterUserData registerUserData = new RegisterUserData();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        registerUserData.setUsername("testuser");
        registerUserData.setPassword("password");
        registerUserData.setPasswordConfirm("password");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    // positive test
    @Test
    void shouldCreateUserSuccessfully() {
        when(userRepository.existsByUsername(registerUserData.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerUserData.getPassword())).thenReturn("hashedpassword");

        when (userRepository.save(any(Users.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Users result = userService.register(registerUserData);
        assertThat(result.getUserid()).isNotNull();
        assertThat(result.getCreatedAt()).isNotNull();

        assertEquals("testuser", result.getUsername());
        assertEquals("hashedpassword", result.getPasswordHash());

        verify(userRepository, times(1)).save(any(Users.class));
        verify(userRepository, times(1)).existsByUsername(registerUserData.getUsername());
    }

    // negative test
    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        when(userRepository.existsByUsername(registerUserData.getUsername())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(registerUserData));

        verify(userRepository, never()).save(any(Users.class));
    }
}