package com.josereis.usermanagerapi.service.jwt.impl;

import com.josereis.usermanagerapi.domain.dto.response.UserAuthenticatedResponse;
import com.josereis.usermanagerapi.domain.entity.authentication.UserAuthenticated;
import com.josereis.usermanagerapi.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    @Value("${api-security.jwt.token.expiration-time}")
    public Long expiration;

    @Value("${spring.application.name}")
    public String applicationName;

    @Override
    public UserAuthenticatedResponse generateToken(Authentication authentication) {
        Instant issuedAt = Instant.now();

        String scopes = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        UserAuthenticated userAuthenticated = (UserAuthenticated) authentication.getPrincipal();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(applicationName).issuedAt(issuedAt)
                .expiresAt(issuedAt.plusMillis(expiration))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .claim("userId", userAuthenticated.getUserId())
                .build();

        return UserAuthenticatedResponse.builder()
                .expiration(claims.getExpiresAt().getEpochSecond())
                .userId(userAuthenticated.getUserId())
                .userName(userAuthenticated.getUsername())
                .token(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .build();
    }

    @Override
    public Boolean validateToken(String token) {
        if(!ObjectUtils.isEmpty(token)) {
            Jwt jwt = this.decode(token);
            if(!ObjectUtils.isEmpty(jwt)) {
                Boolean isExpired = this.isExpired(Objects.requireNonNull(jwt.getExpiresAt()));
                if(isExpired) {
                    return Boolean.FALSE;
                }
            }

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public String getUsername(String token) {
        Jwt jwt = this.decode(token);
        return jwt.getSubject();
    }

    private Boolean isExpired(Instant expiresAt) {
        return expiresAt.isBefore(Instant.now());
    }

    private Jwt decode(String token) {
        return this.jwtDecoder.decode(token);
    }
}
