package com.advjava.hospitalmanagement.repositories;

import com.advjava.hospitalmanagement.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findBySpecialization(String specialization);
}
