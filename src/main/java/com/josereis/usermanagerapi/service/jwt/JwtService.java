package com.josereis.usermanagerapi.service.jwt;

import com.josereis.usermanagerapi.domain.dto.response.UserAuthenticatedResponse;
import org.springframework.security.core.Authentication;

public interface JwtService {
    UserAuthenticatedResponse generateToken(Authentication authentication);
    Boolean validateToken(String token);
    String getUsername(String token);
}