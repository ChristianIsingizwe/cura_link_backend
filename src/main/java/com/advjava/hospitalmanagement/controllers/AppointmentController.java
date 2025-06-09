package com.advjava.hospitalmanagement.controllers;

import com.advjava.hospitalmanagement.dtos.AppointmentDto;
import com.advjava.hospitalmanagement.dtos.CreateAppointmentRequest;
import com.advjava.hospitalmanagement.entities.Appointment;
import com.advjava.hospitalmanagement.mappers.AppointmentMapper;
import com.advjava.hospitalmanagement.repositories.AppointmentRepository;
import com.advjava.hospitalmanagement.repositories.DoctorRepository;
import com.advjava.hospitalmanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;


    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(
            @RequestBody CreateAppointmentRequest req
    ) {
        var patient = userRepository
                .findById(req.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient not found"));
        var doctor = doctorRepository
                .findById(req.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));

        var appointment = new Appointment();
        appointment.setAppointmentTime(req.getAppointmentTime());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setApproved(false);
        appointmentRepository.save(appointment);

        var dto = new AppointmentDto(
                appointment.getId(),
                patient.getId(),
                doctor.getId(),
                appointment.getAppointmentTime(),
                appointment.getApproved()
        );
        return ResponseEntity.ok(dto);
    }


    @PatchMapping("/update/approve/{id}")
    public ResponseEntity<?> approveAppointment(@PathVariable Integer id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.approveAppointment();
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
