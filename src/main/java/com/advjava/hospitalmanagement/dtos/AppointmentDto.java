package com.advjava.hospitalmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentDto {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private LocalDateTime appointmentTime;
    private Boolean approved;
}
