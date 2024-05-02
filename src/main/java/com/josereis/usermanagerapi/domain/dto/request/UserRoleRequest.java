package com.josereis.usermanagerapi.domain.dto.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserRoleRequest {
    private UUID userId;
    private String roleName;
}
