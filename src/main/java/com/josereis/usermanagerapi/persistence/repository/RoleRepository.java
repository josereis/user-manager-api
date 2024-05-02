package com.josereis.usermanagerapi.persistence.repository;

import com.josereis.usermanagerapi.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findAllByNameIn(List<String> names);
}
