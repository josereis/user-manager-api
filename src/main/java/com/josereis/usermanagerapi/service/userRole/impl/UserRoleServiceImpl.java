package com.josereis.usermanagerapi.service.userRole.impl;

import com.josereis.usermanagerapi.domain.dto.response.UserRoleResponse;
import com.josereis.usermanagerapi.domain.entity.Role;
import com.josereis.usermanagerapi.domain.entity.User;
import com.josereis.usermanagerapi.domain.entity.UserRole;
import com.josereis.usermanagerapi.domain.mappers.UserRoleMapper;
import com.josereis.usermanagerapi.persistence.repository.RoleRepository;
import com.josereis.usermanagerapi.persistence.repository.UserRepository;
import com.josereis.usermanagerapi.persistence.repository.UserRoleRepository;
import com.josereis.usermanagerapi.persistence.specification.userRole.UserRoleSpecificationBuilder;
import com.josereis.usermanagerapi.service.userRole.UserRoleService;
import com.josereis.usermanagerapi.shared.exception.BusinessRuleException;
import com.josereis.usermanagerapi.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserRoleMapper mapper;

    @Override
    public void delete(UUID userId, List<UUID> uuids) {
        User user = this.checkUser(userId);
        List<UserRole> userRolesToDelete = this.list(uuids);

        this.validateDelete(user, userRolesToDelete);
        userRolesToDelete.forEach(ur->ur.setDeleted(Boolean.TRUE));

        this.saveAll(userRolesToDelete);
    }

    @Override
    public List<UserRoleResponse> create(User user, List<String> rolesName) {
        List<UserRole> userRoles = new ArrayList<>();
        if(!ObjectUtils.isEmpty(rolesName)) {
            List<Role> roles = this.checkRolesName(rolesName);

            userRoles = this.builderUsersRoles(user, roles);
            userRoles = this.saveAll(userRoles);
        }

        return this.mapper.toResponse(userRoles);
    }

    @Override
    public List<UserRoleResponse> create(UUID userId, List<String> rolesName) {
        User user = this.checkUser(userId);
        List<Role> roles = this.checkRolesName(rolesName);

        List<UserRole> userRoles = this.builderUsersRoles(user, roles);
        userRoles = this.saveAll(userRoles);

        return this.mapper.toResponse(userRoles);
    }

    @Override
    public Page<UserRoleResponse> list(UUID userId, String roleName, Pageable pageable) {
        Specification<UserRole> spec = UserRoleSpecificationBuilder.builderFilter(userId, null, roleName);
        return this.repository.findAll(spec, pageable).map(this.mapper::toResponse);
    }

    private List<UserRole> saveAll(List<UserRole> toSave) {
        return this.repository.saveAllAndFlush(toSave);
    }

    private List<UserRole> list(List<UUID> uuids) {
        return this.repository.findAllByUuidIn(uuids);
    }

    private List<UserRole> builderUsersRoles(User user, List<Role> roles) {
        return roles.stream().map(r -> this.builderUserRole(user, r)).collect(Collectors.toList());
    }

    private User checkUser(UUID userId) {
        return this.userRepository.findByUuid(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private List<Role> checkRolesName(List<String> rolesNames) {
        List<Role> roles = this.roleRepository.findAllByNameIn(rolesNames);
        if(ObjectUtils.isEmpty(roles)) {
            throw new BusinessRuleException("No Roles were found for the names entered.");
        }

        return roles;
    }

    private UserRole builderUserRole(User user, Role role) {
        return UserRole.builder()
                .user(user)
                .role(role)
                .build();
    }

    private void validateDelete(User user, List<UserRole> userRoles) {
        if(ObjectUtils.isEmpty(userRoles)) {
            throw new EntityNotFoundException(UserRole.class.getName());
        }

        List<UserRole> notBelongToUser = userRoles.stream().filter(ur -> !Objects.equals(user.getId(), ur.getUser().getId())).toList();
        if(!ObjectUtils.isEmpty(notBelongToUser)) {
            throw new BusinessRuleException("\n" +
                    "Records not belonging to the informed user were reported.");
        }
    }
}
