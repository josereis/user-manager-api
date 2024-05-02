package com.josereis.usermanagerapi.domain.mappers;

import com.josereis.usermanagerapi.domain.dto.request.UserRoleRequest;
import com.josereis.usermanagerapi.domain.dto.response.UserRoleResponse;
import com.josereis.usermanagerapi.domain.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {RoleMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserRoleMapper extends GenericMapper<UserRole, UserRoleRequest, UserRoleResponse> {
}
