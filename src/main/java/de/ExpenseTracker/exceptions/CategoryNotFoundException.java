package de.ExpenseTracker.exceptions;

/**
 * Thrown when a category with a given UUID does not exist.
 * Typically thrown from a CategoryService and handled by the GlobalExceptionHandler
 */
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(ErrorCode code) {
        super(code.name());
    }
}