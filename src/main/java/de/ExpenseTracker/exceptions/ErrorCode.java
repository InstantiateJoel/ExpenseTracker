package de.ExpenseTracker.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND,
    USER_EXISTS,
    PASSWORD_MISMATCH,
    CATEGORY_NOT_FOUND,
    INVALID_CREDENTIALS,
    EXPENSE_NOT_FOUND,
    INCOME_NOT_FOUND
}