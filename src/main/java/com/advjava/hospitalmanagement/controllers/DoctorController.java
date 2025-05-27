package com.advjava.hospitalmanagement.controllers;


import com.advjava.hospitalmanagement.dtos.CreateDoctorRequest;
import com.advjava.hospitalmanagement.mappers.DoctorMapper;
import com.advjava.hospitalmanagement.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @GetMapping
    public ResponseEntity<?> getAllAvailableDoctors() {
        var doctors = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody CreateDoctorRequest request){
        var doctor = doctorMapper.toEntity(request);
        doctorRepository.save(doctor);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/{specialization}")
    public ResponseEntity<?> getDoctorBySpecialty( @PathVariable String specialization){
        var doctors = doctorRepository.findBySpecialization(specialization);
        if (doctors == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id){
        var doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable Long id){
        var doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        doctorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
