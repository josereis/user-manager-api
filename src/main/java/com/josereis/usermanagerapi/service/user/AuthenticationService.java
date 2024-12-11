package com.josereis.usermanagerapi.service.user;

import com.josereis.usermanagerapi.domain.dto.response.UserAuthenticatedResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    UserAuthenticatedResponse authenticate(Authentication authentication);
}