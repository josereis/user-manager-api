package com.josereis.usermanagerapi.domain.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserAuthenticatedResponse {
    private String token;
    private UUID userId;
    private String userName;
    private Long expiration;
}
