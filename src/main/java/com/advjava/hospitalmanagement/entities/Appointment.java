package com.advjava.hospitalmanagement.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_time")
    private LocalDateTime appointmentTime;

    @Column(name = "approved")
    private Boolean approved = false;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    public void ApproveAppointment() {
        this.approved = true;
    }

}