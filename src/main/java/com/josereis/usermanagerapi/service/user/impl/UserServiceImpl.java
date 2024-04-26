package com.josereis.usermanagerapi.service.user.impl;

import com.josereis.usermanagerapi.domain.dto.request.UserRequest;
import com.josereis.usermanagerapi.domain.dto.response.UserResponse;
import com.josereis.usermanagerapi.domain.entity.User;
import com.josereis.usermanagerapi.domain.entity.authentication.UserAuthenticated;
import com.josereis.usermanagerapi.domain.mappers.UserMapper;
import com.josereis.usermanagerapi.persistence.repository.UserRepository;
import com.josereis.usermanagerapi.service.user.UserService;
import com.josereis.usermanagerapi.shared.exception.BusinessRuleException;
import com.josereis.usermanagerapi.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public UserResponse register(UserRequest request) {
        if(!ObjectUtils.isEmpty(this.checkExistence(request.getUsername()))) {
            throw new BusinessRuleException("No Roles were found for the names entered.");
        }

        User user = this.builderUser(request);
        user = this.save(user);

        UserResponse response = this.mapper.toResponse(user);

        return response;
    }

    @Override
    public UserResponse read(UUID uuid) {
        User entity = this.checkExistence(uuid);
        if(ObjectUtils.isEmpty(entity)) {
            throw new EntityNotFoundException("User not found.");
        }

        return this.mapper.toResponse(entity);
    }

    @Override
    public User internalRead(UUID uuid) {
        return this.checkExistence(uuid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username)
                .map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private User builderUser(UserRequest request) {
        String encryptedPassword = new BCryptPasswordEncoder()
                .encode(request.getPassword());

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encryptedPassword)
                .build();
    }

    private User checkExistence(UUID uuid) {
        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    private User checkExistence(String username) {
        return this.repository.findByUsername(username).orElse(null);
    }

    private User save(User toSave) {
        return this.repository.saveAndFlush(toSave);
    }
}
