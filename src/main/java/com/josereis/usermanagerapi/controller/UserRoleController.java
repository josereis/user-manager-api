package com.josereis.usermanagerapi.controller;

import com.josereis.usermanagerapi.configurarion.security.police.UserRolesPolicy;
import com.josereis.usermanagerapi.domain.dto.response.UserRoleResponse;
import com.josereis.usermanagerapi.service.userRole.UserRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "User - Roles")
@RestController
@RequestMapping("/user/{userId}/roles")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService service;

    @GetMapping
    public ResponseEntity<Page<UserRoleResponse>> list(
            @PathVariable(name = "userId") UUID userId,
            @RequestParam(required = false) String roleName,
            @ParameterObject @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(this.service.list(userId, roleName, pageable));
    }

    @UserRolesPolicy.canCreate
    @PostMapping
    public ResponseEntity<List<UserRoleResponse>> create(@PathVariable(name = "userId") UUID userId, @RequestBody @Valid List<String> request, UriComponentsBuilder uriBuilder) {
        List<UserRoleResponse> response = this.service.create(userId, request);
        URI uri = uriBuilder.path("/user/{userId}/roles")
                .buildAndExpand(userId).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @UserRolesPolicy.canDelete
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable(name = "userId") UUID userId, @RequestParam List<UUID> uuids) {
        this.service.delete(userId, uuids);
        return ResponseEntity.noContent().build();
    }
}
