package com.josereis.usermanagerapi.service.role.impl;

import com.josereis.usermanagerapi.domain.dto.request.RoleRequest;
import com.josereis.usermanagerapi.domain.dto.response.RoleResponse;
import com.josereis.usermanagerapi.domain.entity.Role;
import com.josereis.usermanagerapi.domain.mappers.RoleMapper;
import com.josereis.usermanagerapi.persistence.repository.RoleRepository;
import com.josereis.usermanagerapi.persistence.specification.role.RoleSpecificationBuilder;
import com.josereis.usermanagerapi.service.role.RoleService;
import com.josereis.usermanagerapi.shared.exception.BusinessRuleException;
import com.josereis.usermanagerapi.shared.exception.EntityNotFoundException;
import com.josereis.usermanagerapi.shared.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    private final RoleMapper mapper;

    @Override
    public Page<RoleResponse> list(String name, Boolean active, Pageable pageable) {
        Specification<Role> filter = RoleSpecificationBuilder.builderFilter(name, active);
        return this.repository.findAll(filter, pageable).map(this.mapper::toResponse);
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        Role entity = this.toEntity(request);
        this.validCreateRole(entity);

        return this.mapper
                .toResponse(this.save(entity));
    }

    @Override
    public RoleResponse read(UUID uuid) {
        Role role = this.checkExistence(uuid);
        return this.mapper.toResponse(role);
    }

    @Override
    public RoleResponse update(UUID uuid, RoleRequest request) {
        Role current = this.checkExistence(uuid);
        Role update = this.toEntity(request);
        this.validUpdateRole(current, update);

        BeanUtils.copyProperties(update, current,"id", "uuid", "version", "deleted");
        current = this.save(current);

        return this.mapper.toResponse(current);
    }

    @Override
    public void delete(UUID uuid) {
        Role role = this.checkExistence(uuid);
        role.setDeleted(true);
        this.save(role);
    }

    private Role save(Role toSave) {
        return this.repository.saveAndFlush(toSave);
    }

    private Role checkExistence(String name) {
        return this.repository.findByName(name).orElse(null);
    }

    private Role checkExistence(UUID uuid) {
        return this.repository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Role not found."));
    }

    private void validCreateRole(Role entity) {
        if(!ObjectUtils.isEmpty(this.checkExistence(entity.getName()))) {
            throw new BusinessRuleException("Role already exists for the name entered.");
        }
    }

    private void validUpdateRole(Role current, Role update) {
        if(!Objects.equals(current.getName(), update.getName())) {
            if(!ObjectUtils.isEmpty(this.checkExistence(update.getName()))) {
                throw new BusinessRuleException("Role already exists for the name entered.");
            }
        }
    }

    private Role toEntity(RoleRequest request) {
        Role entity = this.mapper.toEntity(request);
        entity.setPrettyName(StringUtils.removeSpace(entity.getName()));
        entity.setName(StringUtils.normalizeIdentifier(entity.getName()));

        return entity;
    }
}
