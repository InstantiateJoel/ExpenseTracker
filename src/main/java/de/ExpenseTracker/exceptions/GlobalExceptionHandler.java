package de.ExpenseTracker.exceptions;

import de.ExpenseTracker.dto.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * Central exception handler for the application
 * <p>
 * This Class intercepts exceptions thrown by services or controllers
 * and converts them into meaningful HTTP responses with status codes
 * and message keys for the frontend.
 * </p>
 * <p>Handled exceptions:</p>
 *     <ul>
 * <li>{@link CategoryNotFoundException} -> 404 NOT FOUND</li>
 * <li>{@link InvalidCredentialsException} -> 400 BAD REQUEST</li>
 * <li>{@link UserAlreadyExistsException} -> 409 CONFLICT</li>
 * <li>{@link UserNotFoundException} -> 404 NOT FOUND</li>
 * </ul>
 */
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseData handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return toResponseData(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseData handleInvalidCredentialsException(InvalidCredentialsException e) {
        return toResponseData(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseData handleUserNotFoundException(UserNotFoundException e) {
        return toResponseData(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseData handleCategoryNotFoundException(CategoryNotFoundException e) {
        return toResponseData(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        return ResponseData.builder()
                .messageKey(message)
                .build();
    }

    private ResponseData toResponseData(RuntimeException e) {
        Locale locale = getLocale();
        return ResponseData.builder()
                .messageKey(messageSource.getMessage(e.getMessage(), null, locale))
                .build();
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}