package com.josereis.usermanagerapi.service.user.impl;

import com.josereis.usermanagerapi.domain.dto.response.UserAuthenticatedResponse;
import com.josereis.usermanagerapi.service.jwt.JwtService;
import com.josereis.usermanagerapi.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;

    @Override
    public UserAuthenticatedResponse authenticate(Authentication authentication) {
        return this.jwtService.generateToken(authentication);
    }
}
