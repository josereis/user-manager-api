package com.josereis.usermanagerapi.domain.mappers;

import com.josereis.usermanagerapi.domain.dto.request.RoleRequest;
import com.josereis.usermanagerapi.domain.dto.response.RoleResponse;
import com.josereis.usermanagerapi.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper extends GenericMapper<Role, RoleRequest, RoleResponse> {

    @Override
    @Mappings({
            @Mapping(source = "name", target = "prettyName")
    })
    Role toEntity(RoleRequest roleRequest);

    @Override
    @Mappings({
            @Mapping(source = "prettyName", target = "name")
    })
    RoleResponse toResponse(Role role);
}
