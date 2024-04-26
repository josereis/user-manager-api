package com.josereis.usermanagerapi.service.jwt;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);
    Boolean validateToken(String token);
    String getUsername(String token);
}