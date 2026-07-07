package de.ExpenseTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserData {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}