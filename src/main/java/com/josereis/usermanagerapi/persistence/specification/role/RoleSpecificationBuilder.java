package com.josereis.usermanagerapi.persistence.specification.role;

import com.josereis.usermanagerapi.domain.entity.Role;
import com.josereis.usermanagerapi.shared.utils.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class RoleSpecificationBuilder {
    public static Specification<Role> builderFilter(String name, Boolean active) {
        Specification<Role> spec = filterByName(name, null);
        spec = filterByActive(active, spec);

        return spec;
    }

    private static Specification<Role> filterByName(String name, Specification<Role> current) {
        if(!ObjectUtils.isEmpty(name)) {
            return RoleSpecification.checkSpecification(current, RoleSpecification.name(StringUtils.normalizeIdentifier(name)), "and");
        }

        return current;
    }

    private static Specification<Role> filterByActive(Boolean active, Specification<Role> current) {
        if(!ObjectUtils.isEmpty(active)) {
            return RoleSpecification.checkSpecification(current, RoleSpecification.active(active), "and");
        }

        return current;
    }
}
