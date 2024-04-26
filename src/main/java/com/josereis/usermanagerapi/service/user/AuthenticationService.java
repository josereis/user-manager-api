package com.josereis.usermanagerapi.service.user;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    String authenticate(Authentication authentication);
}