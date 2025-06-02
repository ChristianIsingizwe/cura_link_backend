package com.advjava.hospitalmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateAppointmentRequest {
    private LocalDateTime appointmentTime;
    private Integer patientId;
    private Integer doctorId;
}
