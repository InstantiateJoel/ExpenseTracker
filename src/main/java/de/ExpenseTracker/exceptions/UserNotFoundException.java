package de.ExpenseTracker.exceptions;

/**
 * Thrown when a username is not found
 * Typically thrown from a UserService and handled by the GlobalExceptionHandler
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(ErrorCode code) {
        super(code.getCode());
    }
}