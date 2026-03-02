package de.ExpenseTracker.exceptions;

/**
 * Thrown when the password and password confirm do not match
 * Typically thrown from a UserService and handled in the GlobalExceptionHandler
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(ErrorCode code) {
        super(code.getCode());
    }
}