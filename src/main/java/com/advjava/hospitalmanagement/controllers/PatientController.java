package com.advjava.hospitalmanagement.controllers;

import com.advjava.hospitalmanagement.dtos.UserRegisterRequest;
import com.advjava.hospitalmanagement.entities.UserRole;
import com.advjava.hospitalmanagement.mappers.UserMapper;
import com.advjava.hospitalmanagement.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {

    private final UserRepository userRepository;
    private final UserMapper patientMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map
            .of("message", "Email already exists."));
        }
        var patient = patientMapper.toEntity(request);
        System.out.println(patient.getFullName());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setRole(UserRole.PATIENT);
        userRepository.save(patient);

        var patientDto = patientMapper.toDto(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer id) {
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients() {
        var patients = userRepository.findAll();
        var patientDtos =patients.stream().map(patientMapper::toDto).toList();
        return ResponseEntity.ok(patientDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id) {
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var patientDto = patientMapper.toDto(userRepository.findById(id).get());
        return ResponseEntity.ok(patientDto);
    }

}
