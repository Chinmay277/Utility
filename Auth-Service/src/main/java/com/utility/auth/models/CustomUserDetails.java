package com.utility.auth.models;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.utility.auth.entity.Role;
import com.utility.auth.entity.UserCredentials;

/**
 * Custom implementation of Spring Security's UserDetails.
 * Wraps UserCredentials entity and exposes security-related information.
 */
public class CustomUserDetails implements UserDetails {

    private final UserCredentials user;

    public CustomUserDetails(UserCredentials user) {
        this.user = user;
    }

    /**
     * Map roles from UserCredentials into Spring Security authorities.
     * Example: ROLE_INSPECTOR -> new SimpleGrantedAuthority("ROLE_INSPECTOR")
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // already hashed with BCrypt
    }

    @Override
    public String getUsername() {
        return user.getEmailId(); // use email as unique identifier
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    // Utility domain-specific extension
    public String getDisplayName() {
        return user.getUserName();
    }

    public Long getUserId() {
        return user.getUserId();
    }
}