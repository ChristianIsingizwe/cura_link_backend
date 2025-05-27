package com.advjava.hospitalmanagement.repositories;

import com.advjava.hospitalmanagement.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDoctorId(Long id);
    Optional<Appointment> findByPatientId(Long id);
}
