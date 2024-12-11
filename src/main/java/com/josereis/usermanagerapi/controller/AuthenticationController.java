package com.josereis.usermanagerapi.controller;

import com.josereis.usermanagerapi.domain.dto.request.AuthenticationRequest;
import com.josereis.usermanagerapi.domain.dto.request.UserRequest;
import com.josereis.usermanagerapi.domain.dto.response.UserAuthenticatedResponse;
import com.josereis.usermanagerapi.domain.dto.response.UserResponse;
import com.josereis.usermanagerapi.service.user.AuthenticationService;
import com.josereis.usermanagerapi.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Authenticarion")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticatedResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        UserAuthenticatedResponse token = this.authenticationService.authenticate(this.authenticationManager.authenticate(usernamePassword));

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/auth/login").buildAndExpand().toUri();
        UserResponse response = this.userService.register(request);

        return ResponseEntity.created(uri).body(response);
    }
}