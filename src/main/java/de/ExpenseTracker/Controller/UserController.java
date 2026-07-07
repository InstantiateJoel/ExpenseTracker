package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.RegisterUserData;
import de.ExpenseTracker.dto.ResponseData;
import de.ExpenseTracker.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;

    /**
     * Registers a new user
     *
     * @param registerUserData DTO containing username, password and password confirmation
     * @return ResponseData with username and message key
     */
    @PostMapping("/register")
    public ResponseData register(@Valid @RequestBody RegisterUserData registerUserData) {
        userService.register(registerUserData);

        return ResponseData.builder()
                .messageKey("USER_CREATED")
                .username(registerUserData.getUsername())
                .build();
    }
}