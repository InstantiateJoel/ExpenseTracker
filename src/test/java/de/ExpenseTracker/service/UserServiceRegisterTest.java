package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.RegisterUserData;
import de.ExpenseTracker.exceptions.UserAlreadyExistsException;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceRegisterTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private final RegisterUserData registerUserData = new RegisterUserData();

    @BeforeEach
    void setUp() {
        registerUserData.setUsername("testuser");
        registerUserData.setPassword("password");
        registerUserData.setPasswordConfirm("password");
    }

    // positive test
    @Test
    void shouldCreateUserSuccessfully() {
        when(userRepository.existsByUsername(registerUserData.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerUserData.getPassword())).thenReturn("hashedPassword");

        when (userRepository.save(any(Users.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(Authentication.class));

        Users result = userService.register(registerUserData);

        assertThat(result.getUserid()).isNotNull();
        assertThat(result.getCreatedAt()).isNotNull();

        assertEquals("testuser", result.getUsername());
        assertEquals("hashedPassword", result.getPasswordHash());

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