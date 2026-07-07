package de.ExpenseTracker.exceptions;

public class IncomeNotFoundException extends RuntimeException {
    public IncomeNotFoundException(ErrorCode code) {
        super(code.name());
    }
}
