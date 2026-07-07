package de.ExpenseTracker.exceptions;

/**
 * Thrown when the username already exists when registering
 * Typically thrown from a UserService and handled by the GlobalExceptionHandler
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(ErrorCode code) {
        super(code.name());
    }
}