package com.advjava.hospitalmanagement.mappers;

import com.advjava.hospitalmanagement.dtos.CreateDoctorRequest;
import com.advjava.hospitalmanagement.dtos.DoctorDto;
import com.advjava.hospitalmanagement.entities.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toEntity(CreateDoctorRequest request);
    DoctorDto toDto(Doctor entity);
}
