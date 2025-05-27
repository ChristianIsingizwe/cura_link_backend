package com.advjava.hospitalmanagement.controllers;

import com.advjava.hospitalmanagement.dtos.CreateAppointmentRequest;
import com.advjava.hospitalmanagement.mappers.AppointmentMapper;
import com.advjava.hospitalmanagement.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentRequest request) {
        var appointment = appointmentMapper.toEntity(request);
        appointmentRepository.save(appointment);

        var appointmentDto = appointmentMapper.toDto(appointment);
        return ResponseEntity.ok(appointmentDto);
    }

    @PatchMapping("/update/approve/{id}")
    public ResponseEntity<?> approveAppointment(@PathVariable Long id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.ApproveAppointment();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getAllAppointmentsByDoctorId(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentRepository.findByDoctorId(id));
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<?> getAllAppointmentsByPatientId(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentRepository.findByPatientId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        appointmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
