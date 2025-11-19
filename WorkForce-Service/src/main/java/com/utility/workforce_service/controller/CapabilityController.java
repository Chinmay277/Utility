package com.utility.workforce_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utility.workforce_service.service.CapabilityService;

@RestController
@RequestMapping("/api/v1/capability")
public class CapabilityController {
	
	@Autowired
	CapabilityService capabilityService;
	
	@PostMapping(path = "/{roleId}")
	public ResponseEntity<?> addCapability(@PathVariable("roleId") Long roleId ){
		return null;
		
		
	}

}
