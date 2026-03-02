package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserData {
    @NotBlank(message = "Username can't be empty!")
    @Size(min = 3, max = 20, message = "Username has to be at least 3 characters long and have a maximum length of 20!")
    private String username;

    @NotBlank(message = "Password can't be empty!")
    @Size(min = 8, max = 32, message = "Password has to be at least 8 characters long and have a maximum of 32!")
    private String password;
}