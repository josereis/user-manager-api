package com.josereis.usermanagerapi.service.userRole;

import com.josereis.usermanagerapi.domain.dto.response.UserRoleResponse;
import com.josereis.usermanagerapi.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    void delete(UUID userId, List<UUID> uuids);
    List<UserRoleResponse> create(User user, List<String> rolesName);
    List<UserRoleResponse> create(UUID userId, List<String> rolesName);
    Page<UserRoleResponse> list(UUID userId, String roleName, Pageable pageable);
}
