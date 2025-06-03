package com.advjava.hospitalmanagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "Full names are required")
    private String fullName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one digit, and one special character."
    )
    private String password;
}
