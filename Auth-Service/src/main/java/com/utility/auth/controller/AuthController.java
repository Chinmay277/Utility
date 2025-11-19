package com.utility.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utility.auth.dto.UserCredentialsDto;
import com.utility.auth.entity.UserCredentials;
import com.utility.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserCredentialsDto userCredentials){
		return authService.registerUser(userCredentials);
	}
}
