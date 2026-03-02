package de.ExpenseTracker.service;

import de.ExpenseTracker.dto.RegisterUserData;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.exceptions.InvalidCredentialsException;
import de.ExpenseTracker.exceptions.UserAlreadyExistsException;
import de.ExpenseTracker.exceptions.UserNotFoundException;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new User
     *
     * @param registerUserData the DTO that is given with user credentials
     * @return The created Users entity
     * @throws UserAlreadyExistsException  if the username with the given username already exists
     * @throws InvalidCredentialsException if the password and confirmation do not match
     *
     */
    public Users register(RegisterUserData registerUserData) throws UserAlreadyExistsException {
        if (checkUserExist(registerUserData.getUsername())) {
            throw new UserAlreadyExistsException(ErrorCode.USER_EXISTS);
        }

        if (!registerUserData.getPassword().equals(registerUserData.getPasswordConfirm())) {
            throw new InvalidCredentialsException(ErrorCode.PASSWORD_MISMATCH);
        }

        Users user = Users.builder()
                .userid(UUID.randomUUID())
                .username(registerUserData.getUsername())
                .passwordHash(passwordEncoder.encode(registerUserData.getPassword()))
                .createdAt(Instant.now())
                .build();

        return userRepository.save(user);
    }

    /**
     * Returns the current User from the session.
     * @return the User object
     * @throws UserNotFoundException when the auth object is null
     */
    public Users getCurrentUserFromSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        String username = auth.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private boolean checkUserExist(String username) {
        return userRepository.existsByUsername(username);
    }
}