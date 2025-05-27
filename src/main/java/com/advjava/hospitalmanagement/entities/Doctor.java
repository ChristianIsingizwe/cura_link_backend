package com.advjava.hospitalmanagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "names")
    private String fullName;
    @Column(nullable = false)
    private String specialization;
}