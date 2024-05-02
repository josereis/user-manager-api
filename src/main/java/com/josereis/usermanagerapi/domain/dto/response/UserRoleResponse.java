package com.josereis.usermanagerapi.domain.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserRoleResponse {
    private UUID uuid;
    private RoleResponse role;
}
