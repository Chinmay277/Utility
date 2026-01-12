package com.utility.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Role entity mapped to RoleType enum.
 * Provides flexibility for DB storage and audit.
 */
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "roleName", name = "UK_role_name")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RoleType roleName;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private boolean active = true;
}