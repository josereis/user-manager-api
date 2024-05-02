package com.josereis.usermanagerapi.persistence.specification.userRole;

import com.josereis.usermanagerapi.domain.entity.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class UserRoleSpecificationBuilder {

    public static Specification<UserRole> builderFilter(UUID userId, String username, String roleName) {
        Specification<UserRole> spec = filterByUserId(userId, null);
        spec = filterByUsername(username, spec);
        spec = filterByRoleName(roleName, spec);

        return spec;
    }

    private static Specification<UserRole> filterByUserId(UUID userId, Specification<UserRole> current) {
        if(!ObjectUtils.isEmpty(userId)) {
            return UserRoleSpecification.checkSpecification(current, UserRoleSpecification.user(userId), "and");
        }
        return current;
    }

    private static Specification<UserRole> filterByUsername(String username, Specification<UserRole> current) {
        if(!ObjectUtils.isEmpty(username)) {
            return UserRoleSpecification.checkSpecification(current, UserRoleSpecification.user(username), "and");
        }
        return current;
    }

    private static Specification<UserRole> filterByRoleName(String roleName, Specification<UserRole> current) {
        if(!ObjectUtils.isEmpty(roleName)) {
            return UserRoleSpecification.checkSpecification(current, UserRoleSpecification.role(roleName, true), "and");
        }
        return current;
    }
}
