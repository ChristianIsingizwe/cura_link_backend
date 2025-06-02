package com.advjava.hospitalmanagement.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private LocalDateTime appointmentTime;
    private Boolean approved;
}
