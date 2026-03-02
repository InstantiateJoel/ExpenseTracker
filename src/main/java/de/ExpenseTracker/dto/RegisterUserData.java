package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserData {

    @NotBlank()
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank()
    @Size(min = 8, max = 32)
    private String password;

    @NotBlank()
    @Size(min = 8, max = 32)
    private String passwordConfirm;
}