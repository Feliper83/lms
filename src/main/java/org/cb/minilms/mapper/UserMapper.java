package org.cb.minilms.mapper;


import org.cb.minilms.dto.UserRequest;
import org.cb.minilms.dto.UserResponse;
import org.cb.minilms.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserRequest toDto(User user);
    User toEntity(UserRequest userRequest);


    UserResponse toDtoResponse(User user);



}