package com.utility.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity representing user credentials for authentication.
 * Mature design with audit fields, account status, and role mapping.
 */
@Entity
@Table(name = "user_credentials", uniqueConstraints = {
        @UniqueConstraint(columnNames = "emailId", name = "UK_user_email")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, length = 50)
    private String userName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true, length = 100)
    private String emailId;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Column(nullable = false)
    private String password; // ⚠️ Store only hashed passwords (e.g., BCrypt)

    // Account status flags
    @Column(nullable = false)
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    private boolean enabled = true;

    // Audit fields
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    // Role mapping
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}