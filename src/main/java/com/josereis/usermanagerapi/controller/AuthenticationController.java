package com.josereis.usermanagerapi.controller;

import com.josereis.usermanagerapi.domain.dto.request.AuthenticationRequest;
import com.josereis.usermanagerapi.domain.dto.request.UserRequest;
import com.josereis.usermanagerapi.domain.dto.response.UserAuthenticatedResponse;
import com.josereis.usermanagerapi.domain.dto.response.UserResponse;
import com.josereis.usermanagerapi.service.user.AuthenticationService;
import com.josereis.usermanagerapi.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Authenticarion")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "Connect user to the ecosystem.")
    @PostMapping("/login")
    public ResponseEntity<UserAuthenticatedResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        UserAuthenticatedResponse token = this.authenticationService.authenticate(this.authenticationManager.authenticate(usernamePassword));

        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "Register ecosystem access user.")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/auth/login").buildAndExpand().toUri();
        UserResponse response = this.userService.register(request);

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Confirm user registration.")
    @PatchMapping("/{userId}/confirm-registration")
    public ResponseEntity<Void> confirmRegistration(@PathVariable UUID userId) {
        this.userService.confirmRegistration(userId);
        return ResponseEntity.noContent().build();
    }
}