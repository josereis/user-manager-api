package com.josereis.usermanagerapi.service.user;

import com.josereis.usermanagerapi.domain.dto.request.UserRequest;
import com.josereis.usermanagerapi.domain.dto.response.UserResponse;
import com.josereis.usermanagerapi.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserResponse register(UserRequest request);
    UserResponse read(UUID uuid);
    User internalRead(UUID uuid);
    void confirmRegistration(UUID uuid);
}
