package com.josereis.usermanagerapi.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class AuthenticationRequest {
    @NotBlank(message = "{errors.attributes.user.username.required}")
    private String username;

    @NotBlank(message = "{errors.attributes.user.password.required}")
    private String password;
}
