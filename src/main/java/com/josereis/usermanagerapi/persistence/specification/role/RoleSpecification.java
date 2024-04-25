package com.josereis.usermanagerapi.persistence.specification.role;

import com.josereis.usermanagerapi.domain.entity.Role;
import com.josereis.usermanagerapi.domain.enums.QueryOperatorEnum;
import com.josereis.usermanagerapi.persistence.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification extends GenericSpecification<Role> {
    protected static Specification<Role> name(String name) {
        return startsWith("name", name);
    }

    protected static Specification<Role> active(Boolean active) {
        return createSpecification(builderSpecificationFilter(QueryOperatorEnum.EQUAL, "active", active));
    }
}
