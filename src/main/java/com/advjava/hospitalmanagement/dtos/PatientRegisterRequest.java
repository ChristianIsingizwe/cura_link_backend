package com.advjava.hospitalmanagement.dtos;

import com.advjava.hospitalmanagement.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PatientRegisterRequest {
    private String fullNames;
    private String email;
    private String password;
    private UserRole role;
    private LocalDateTime dateOfBirth;
}
