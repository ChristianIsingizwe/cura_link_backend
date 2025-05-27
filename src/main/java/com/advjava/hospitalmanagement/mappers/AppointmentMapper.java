package com.advjava.hospitalmanagement.mappers;

import com.advjava.hospitalmanagement.dtos.AppointmentDto;
import com.advjava.hospitalmanagement.dtos.CreateAppointmentRequest;
import com.advjava.hospitalmanagement.entities.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toEntity(CreateAppointmentRequest request);
    AppointmentDto toDto(Appointment appointment);
}
