package com.advjava.hospitalmanagement.controllers;

import com.advjava.hospitalmanagement.dtos.PatientRegisterRequest;
import com.advjava.hospitalmanagement.entities.UserRole;
import com.advjava.hospitalmanagement.mappers.UserMapper;
import com.advjava.hospitalmanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private final UserRepository patientRepository;
    private final UserMapper patientMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegisterRequest request) {
        if (patientRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map
            .of("message", "Email already exists."));
        }
        var patient = patientMapper.toEntity(request);
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setRole(UserRole.PATIENT);
        patientRepository.save(patient);

        var patientDto = patientMapper.toDto(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDto);
    }

}
