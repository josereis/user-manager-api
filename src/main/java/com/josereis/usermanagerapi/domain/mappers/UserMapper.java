package com.josereis.usermanagerapi.domain.mappers;

import com.josereis.usermanagerapi.domain.dto.request.UserRequest;
import com.josereis.usermanagerapi.domain.dto.response.UserResponse;
import com.josereis.usermanagerapi.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper extends GenericMapper<User, UserRequest, UserResponse> {
}
