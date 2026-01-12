package com.utility.auth.service;

import com.utility.auth.dao.UserDao;
import com.utility.auth.entity.UserCredentials;
import com.utility.auth.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Custom implementation of UserDetailsService.
 * Loads user information from the database and maps it into Spring Security's UserDetails.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userRepository;

    public CustomUserDetailsService(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username (email in our case).
     * @param username the email identifier
     * @return UserDetails for Spring Security
     * @throws UsernameNotFoundException if user not found or inactive
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials user = userRepository.findByEmailId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Enforce compliance: check if account is active
        if (!user.isEnabled() || !user.isAccountNonLocked()) {
            throw new UsernameNotFoundException("User account is disabled or locked: " + username);
        }

        // Audit: update last login timestamp
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        return new CustomUserDetails(user);
    }
}