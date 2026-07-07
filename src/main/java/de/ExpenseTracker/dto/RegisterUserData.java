package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserData {

    @NotBlank
    @Size(min = 3, max = 20, message = "USERNAME:LENGTH")
    private String username;

    @NotBlank
    @Size(min = 8, max = 32, message="PASSWORD_LENGTH")
    private String password;

    @NotBlank
    private String passwordConfirm;
}