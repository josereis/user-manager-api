package com.josereis.usermanagerapi.domain.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RoleResponse {
    private UUID uuid;
    private String name;
    private Boolean active;
    private String description;
}
