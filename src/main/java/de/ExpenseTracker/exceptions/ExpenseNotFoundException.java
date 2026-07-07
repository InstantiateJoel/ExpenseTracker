package de.ExpenseTracker.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(ErrorCode code) {
        super(code.name());
    }
}
