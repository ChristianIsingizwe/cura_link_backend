package com.advjava.hospitalmanagement.controllers;


import com.advjava.hospitalmanagement.config.JwtConfig;
import com.advjava.hospitalmanagement.dtos.CreateDoctorRequest;
import com.advjava.hospitalmanagement.dtos.JwtResponse;
import com.advjava.hospitalmanagement.dtos.LoginRequest;
import com.advjava.hospitalmanagement.entities.UserRole;
import com.advjava.hospitalmanagement.mappers.DoctorMapper;
import com.advjava.hospitalmanagement.repositories.DoctorRepository;
import com.advjava.hospitalmanagement.services.DoctorJwtService;
import com.advjava.hospitalmanagement.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AuthenticationManager authenticationManager;
    private final DoctorJwtService jwtService;
    private final JwtConfig jwtConfig;

    @GetMapping
    public ResponseEntity<?> getAllDoctors() {
        var doctors = doctorRepository.findAll();
        var doctorDtos =doctors.stream().map(doctorMapper::toDto).toList();
        return ResponseEntity.ok(doctorDtos);
    }

    @PostMapping("/add")
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

    @PostMapping("/login")
    public ResponseEntity<?> loginDoctor(@RequestBody LoginRequest request, HttpServletResponse response) {
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var doctor = doctorRepository.findByEmail(request.getEmail()).orElseThrow();
        var accessToken = jwtService.generateDoctorAccessToken(doctor);
        var refreshToken = jwtService.generateDoctorRefreshToken(doctor);

        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }
}
