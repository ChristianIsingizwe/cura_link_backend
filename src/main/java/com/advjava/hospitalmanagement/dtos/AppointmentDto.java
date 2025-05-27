package com.advjava.hospitalmanagement.dtos;

import java.time.LocalDateTime;

public class AppointmentDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private Boolean approved;
}
