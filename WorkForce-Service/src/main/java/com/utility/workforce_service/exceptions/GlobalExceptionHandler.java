package com.utility.workforce_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({RoleNotFoundException.class})
	public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException roleNotFoundException){
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(roleNotFoundException.getMessage());
	}
	
	@ExceptionHandler({RoleAlreadyExistsException.class})
	public ResponseEntity<?> handleRoleAlreadyExistsException(RoleAlreadyExistsException roleAlreadyExistsException){
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(roleAlreadyExistsException.getMessage());
	}

}
