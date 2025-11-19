package com.utility.workforce_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utility.workforce_service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	boolean existsByRoleName(String role);

	Role findByRoleName(String roleName);
	
}
