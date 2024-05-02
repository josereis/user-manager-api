package com.josereis.usermanagerapi.persistence.repository;

import com.josereis.usermanagerapi.domain.entity.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends GenericRepository<UserRole, Long> {
}
