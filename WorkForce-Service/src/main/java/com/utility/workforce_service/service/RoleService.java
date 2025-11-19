package com.utility.workforce_service.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utility.workforce_service.entity.Capability;
import com.utility.workforce_service.entity.Role;
import com.utility.workforce_service.exceptions.RoleAlreadyExistsException;
import com.utility.workforce_service.exceptions.RoleNotFoundException;
import com.utility.workforce_service.repository.RoleRepository;
import com.utility.workforce_service.req.dto.RoleDto;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository repository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public boolean ifRoleExists(String role) {
		if(repository.existsByRoleName(role)) {
			return true;
		}
		// TODO Auto-generated method stub
		return false;
		
		
	}

	public void addRole(RoleDto roleDto) {
		if(ifRoleExists(roleDto.getRoleName())) {
			throw new RoleAlreadyExistsException("Role Already Exists");
		}
		Role role = new Role();
		role.setRoleName(roleDto.getRoleName());
		Set<Capability> capabilities = new HashSet<>();
		capabilities = roleDto.getCapabilitySets().stream()
							.map(c -> {
							   Capability capability = this.modelMapper.map(c, Capability.class);
							   capability.setRole(role);
							   return capability;
							})
							.collect(Collectors.toSet());
		role.setCapabilities(capabilities);
		repository.save(role);
		// TODO Auto-generated method stub	
	}
	
	public Role getByRoleName(String roleName) {
		return repository.findByRoleName(roleName);
	}

}
