package com.utility.auth.service;

import com.utility.auth.dao.UserDao;
import com.utility.auth.dto.LoginRequest;
import com.utility.auth.dto.UserCredentialsDto;
import com.utility.auth.entity.UserCredentials;
import com.utility.auth.exception.UserAlreadyExistsException;
import com.utility.auth.exception.UserRegistrationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserDao userDao;
	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
	PasswordEncoder encoder;

    public AuthService(AuthenticationManager authenticationManager, 
		JwtService jwtService, UserDao userDao,
		PasswordEncoder encoder
	) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
		this.userDao = userDao;
		this.encoder = encoder;
    }


    /**
     * Registers a new user in the system.
     * @param userCredentialsDto DTO containing user registration details
     * @return ResponseEntity with success or error message
     */
    public ResponseEntity<?> registerUser( UserCredentialsDto userCredentialsDto) {
        try {
            // Check if user already exists
            Optional<UserCredentials> existingUser = userDao.findByEmailId(userCredentialsDto.getEmail());
            if (existingUser.isPresent()) {
                LOGGER.warn("Registration attempt failed: User with email {} already exists", userCredentialsDto.getEmail());
                throw new UserAlreadyExistsException("User with email " + userCredentialsDto.getEmail() + " already exists");
            }
			
            // Map DTO to entity
            UserCredentials userCredentials = mapUserCredDtoToEntity(userCredentialsDto);

            // Save user
            UserCredentials savedUser = userDao.save(userCredentials);
            LOGGER.info("User registered successfully with email {}", savedUser.getEmailId());

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (DataAccessException e) {
            LOGGER.error("Database error occurred while registering user: {}", e.getMessage(), e);
            throw new UserRegistrationException("Internal error occurred while registering user");
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new UserRegistrationException("Unexpected error occurred during registration");
        }
    }

    private UserCredentials mapUserCredDtoToEntity(UserCredentialsDto credentialsDto) {
        UserCredentials credentials = new UserCredentials();
        credentials.setEmailId(credentialsDto.getEmail());
        credentials.setUserName(credentialsDto.getName());
        credentials.setPassword(encoder.encode(credentialsDto.getPassWord())); // ⚠️ In real-world, hash this before saving!
        return credentials;
    }

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            String token = jwtService.generateToken(authentication);

            return ResponseEntity.ok(Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "expires_in", 3600
            ));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401)
                                 .body(Map.of("error", "Invalid email or password"));
        }
    }

}