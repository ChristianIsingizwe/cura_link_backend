package com.advjava.hospitalmanagement.mappers;

import com.advjava.hospitalmanagement.dtos.UserDto;
import com.advjava.hospitalmanagement.dtos.PatientRegisterRequest;
import com.advjava.hospitalmanagement.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(PatientRegisterRequest request);
    UserDto toDto(User user);
}
