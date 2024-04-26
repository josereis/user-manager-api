package com.josereis.usermanagerapi.persistence.repository;

import com.josereis.usermanagerapi.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
