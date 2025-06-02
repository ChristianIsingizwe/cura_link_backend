package com.advjava.hospitalmanagement.controllers;


import com.advjava.hospitalmanagement.dtos.CreateDoctorRequest;
import com.advjava.hospitalmanagement.entities.UserRole;
import com.advjava.hospitalmanagement.mappers.DoctorMapper;
import com.advjava.hospitalmanagement.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @GetMapping
    public ResponseEntity<?> getAllDoctors() {
        var doctors = doctorRepository.findAll();
        var doctorDtos =doctors.stream().map(doctorMapper::toDto).toList();
        return ResponseEntity.ok(doctorDtos);
    }

    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody CreateDoctorRequest request){
        var doctor = doctorMapper.toEntity(request);
        doctor.setRole(UserRole.DOCTOR);
        doctorRepository.save(doctor);

        var doctorDto = doctorMapper.toDto(doctor);
        return ResponseEntity.ok(doctorDto);
    }

    @GetMapping("/{specialization}")
    public ResponseEntity<?> getDoctorBySpecialty( @PathVariable String specialization){
        var doctors = doctorRepository.findBySpecialization(specialization);
        if (doctors == null) {
            return ResponseEntity.notFound().build();
        }
        var doctorDtos =doctors.stream().map(doctorMapper::toDto).toList();
        return ResponseEntity.ok(doctorDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id){
        var doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var doctorDto = doctorMapper.toDto(doctor.get());
        return ResponseEntity.ok(doctorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable Integer id){
        var doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doctorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
