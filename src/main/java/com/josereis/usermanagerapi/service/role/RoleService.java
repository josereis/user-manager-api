package com.josereis.usermanagerapi.service.role;

import com.josereis.usermanagerapi.domain.dto.request.RoleRequest;
import com.josereis.usermanagerapi.domain.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleService {
    Page<RoleResponse> list(String name, Boolean active, Pageable pageable);
    RoleResponse create(RoleRequest request);
    RoleResponse read(UUID uuid);
    RoleResponse update(UUID uuid, RoleRequest request);
    void delete(UUID uuid);
}
