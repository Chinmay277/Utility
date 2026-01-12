package com.utility.auth.controller;

import com.utility.auth.dto.LoginRequest;
import com.utility.auth.dto.UserCredentialsDto;
import com.utility.auth.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * REST controller for authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCredentialsDto userCredentials) {
        LOGGER.info("Signup request received for email: {}", userCredentials.getEmail());
        return authService.registerUser(userCredentials);
    }

    /**
     * Authenticates a user and returns a JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.info("Login attempt for email: {}", loginRequest.getEmail());
        return authService.authenticateUser(loginRequest);
    }

	@GetMapping("test")
	public String authtest() {
		return new String("Test Done, Successful");
	}
	
}