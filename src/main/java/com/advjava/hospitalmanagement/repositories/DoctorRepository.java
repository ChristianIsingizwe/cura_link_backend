package com.advjava.hospitalmanagement.repositories;

import com.advjava.hospitalmanagement.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findBySpecialty(String specialty);
    List<Doctor> findBySpecialization(String specialization);
}
