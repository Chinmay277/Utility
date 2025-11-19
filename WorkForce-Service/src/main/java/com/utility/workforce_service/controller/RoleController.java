package com.utility.workforce_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utility.workforce_service.exceptions.RoleAlreadyExistsException;
import com.utility.workforce_service.req.dto.RoleDto;
import com.utility.workforce_service.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
	
	@Autowired
	RoleService roleSvc;
	
	@PostMapping(value = "/ifExists/{role}")
	public ResponseEntity<?> ifRoleExists(@PathVariable("role") String role){
		if(roleSvc.ifRoleExists(role)) {
			return ResponseEntity.ok().body("Role Exists");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role Doesn't Exists");
	}
	
	@PostMapping
	public ResponseEntity<?> addRole(@RequestBody RoleDto roleDto){
		roleSvc.addRole(roleDto);
		return ResponseEntity.ok().body("Role added successfully!");
	}

}
