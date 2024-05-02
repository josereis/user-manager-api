package com.josereis.usermanagerapi.controller;

import com.josereis.usermanagerapi.configurarion.security.police.RolePolicy;
import com.josereis.usermanagerapi.domain.dto.request.RoleRequest;
import com.josereis.usermanagerapi.domain.dto.response.RoleResponse;
import com.josereis.usermanagerapi.service.role.RoleService;
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
import java.util.UUID;

@Tag(name = "Role")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @ParameterObject @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(this.service.list(name, active, pageable));
    }

    @RolePolicy.canCreate
    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody @Valid RoleRequest request, UriComponentsBuilder uriBuilder) {
        RoleResponse response = this.service.create(request);
        URI uri = uriBuilder.path("/role/{id}")
                .buildAndExpand(response.getUuid()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RoleResponse> read(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(this.service.read(uuid));
    }

    @RolePolicy.canUpdate
    @PutMapping("/{uuid}")
    public ResponseEntity<RoleResponse> update(@PathVariable UUID uuid, @RequestBody @Valid RoleRequest request) {
        RoleResponse response = this.service.update(uuid, request);

        return ResponseEntity.accepted().body(response);
    }

    @RolePolicy.canDelete
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        this.service.delete(uuid);

        return ResponseEntity.noContent().build();
    }
}
