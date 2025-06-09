package com.advjava.hospitalmanagement.repositories;

import com.advjava.hospitalmanagement.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findBySpecialization(String specialization);
    Optional<Doctor> findByEmail(String email);
}
