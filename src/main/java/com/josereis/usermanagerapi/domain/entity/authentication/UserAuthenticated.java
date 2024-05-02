package com.josereis.usermanagerapi.domain.entity.authentication;

import com.josereis.usermanagerapi.domain.entity.User;
import com.josereis.usermanagerapi.domain.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UserAuthenticated implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> userRoles = this.user.getRoles();
        if(!ObjectUtils.isEmpty(userRoles)) {
            return userRoles.stream()
                    .map(userRole -> userRole.getRole().getName())
                    .map(SimpleGrantedAuthority::new).toList();
        }

        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    public UUID getUserId() {
        return this.user.getUuid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
