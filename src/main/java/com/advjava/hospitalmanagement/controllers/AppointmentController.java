package com.advjava.hospitalmanagement.controllers;

import com.advjava.hospitalmanagement.dtos.CreateAppointmentRequest;
import com.advjava.hospitalmanagement.mappers.AppointmentMapper;
import com.advjava.hospitalmanagement.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentRequest request) {
        var appointment = appointmentMapper.toEntity(request);
        appointmentRepository.save(appointment);

        var appointmentDto = appointmentMapper.toDto(appointment);
        return ResponseEntity.ok(appointmentDto);
    }

    @PatchMapping("/update/approve/{id}")
    public ResponseEntity<?> approveAppointment(@PathVariable Integer id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.ApproveAppointment();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getAllAppointmentsByDoctorId(@PathVariable Integer id) {
        var appointments = appointmentRepository.findByDoctorId(id);
        var appointmentsDtos = appointments.stream().map(appointmentMapper::toDto).toList();
        return ResponseEntity.ok(appointmentsDtos);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<?> getAllAppointmentsByPatientId(@PathVariable Integer id) {
        var appointments = appointmentRepository.findByPatientId(id);
        var appointmentsDtos = appointments.stream().map(appointmentMapper::toDto).toList();
        return ResponseEntity.ok(appointmentsDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        appointmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
