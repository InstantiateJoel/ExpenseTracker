package de.ExpenseTracker.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("USER.NOT_FOUND"),
    USER_EXISTS("USER.EXISTS"),
    PASSWORD_MISMATCH("USER.PASSWORD_MISMATCH"),
    PASSWORD_INCORRECT("USER.PASSWORD_INCORRECT"),
    CATEGORY_NOT_FOUND("CATEGORY.NOT_FOUND");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }
}