package com.josereis.usermanagerapi.persistence.specification.userRole;

import com.josereis.usermanagerapi.domain.entity.Role;
import com.josereis.usermanagerapi.domain.entity.User;
import com.josereis.usermanagerapi.domain.entity.UserRole;
import com.josereis.usermanagerapi.persistence.specification.GenericSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserRoleSpecification extends GenericSpecification<UserRole> {

    protected static Specification<UserRole> user(UUID userId) {
        return (Root<UserRole> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<UserRole, User> userJoin = root.join("user");

            return builder.equal(userJoin.get("uuid"), userId);
        };
    }

    protected static Specification<UserRole> user(String username) {
        return (Root<UserRole> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<UserRole, User> userJoin = root.join("user");

            return builder.equal(userJoin.get("username"), username);
        };
    }

    protected static Specification<UserRole> role(String roleName, boolean isEqual) {
        return (Root<UserRole> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<UserRole, Role> roleJoin = root.join("role");

            return isEqual ? builder.equal(roleJoin.get("name"), roleName) :
                    builder.like(roleJoin.get("name"), "%"+roleName+"%");
        };
    }
}
