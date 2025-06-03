package com.advjava.hospitalmanagement.mappers;

import com.advjava.hospitalmanagement.dtos.UserDto;
import com.advjava.hospitalmanagement.dtos.UserRegisterRequest;
import com.advjava.hospitalmanagement.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "fullName", target = "fullName")
    User toEntity(UserRegisterRequest request);
    UserDto toDto(User user);
}
