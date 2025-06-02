package com.advjava.hospitalmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserRegisterRequest {
    private String fullNames;
    private String email;
    private String password;
    private LocalDateTime dateOfBirth;
}
