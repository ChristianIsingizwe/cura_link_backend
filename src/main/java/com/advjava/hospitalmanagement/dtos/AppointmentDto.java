package com.advjava.hospitalmanagement.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private LocalDate appointmentTime;
    private Boolean approved;
}
