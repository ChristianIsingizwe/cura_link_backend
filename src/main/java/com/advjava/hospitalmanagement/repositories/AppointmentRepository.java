package com.advjava.hospitalmanagement.repositories;

import com.advjava.hospitalmanagement.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findByDoctorId(Integer id);
    Optional<Appointment> findByPatientId(Integer id);
}
