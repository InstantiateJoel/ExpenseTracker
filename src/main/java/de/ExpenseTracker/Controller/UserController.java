package de.ExpenseTracker.Controller;

import de.ExpenseTracker.dto.RegisterUserData;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @PostMapping("/register")
    public Users register(
            @RequestBody RegisterUserData data,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return userService.register(data, request, response);
    }
}