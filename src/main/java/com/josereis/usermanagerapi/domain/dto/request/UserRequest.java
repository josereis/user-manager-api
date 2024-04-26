package com.josereis.usermanagerapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserRequest {
    private String name;

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;
}
