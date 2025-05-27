package com.advjava.hospitalmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDoctorRequest {
    private String fullName;
    private String email;
    private String specialty;
}
