package com.josereis.usermanagerapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RoleRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    private String description;
}
