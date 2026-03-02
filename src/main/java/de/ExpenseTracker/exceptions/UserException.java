package de.ExpenseTracker.exceptions;

public class UserException extends RuntimeException {
    public UserException(String key) {
        super(key);
    }
}
