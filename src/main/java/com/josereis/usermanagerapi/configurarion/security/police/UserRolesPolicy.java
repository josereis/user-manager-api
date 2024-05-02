package com.josereis.usermanagerapi.configurarion.security.police;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface UserRolesPolicy {
    @PreAuthorize("hasAnyRole({'OWNER', 'ADMIN', 'API'})")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface canCreate {
    }

    @PreAuthorize("hasAnyRole({'OWNER', 'ADMIN', 'API'})")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface canUpdate {
    }

    @PreAuthorize("hasAnyRole({'OWNER', 'ADMIN', 'API'})")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface canDelete {
    }
}
