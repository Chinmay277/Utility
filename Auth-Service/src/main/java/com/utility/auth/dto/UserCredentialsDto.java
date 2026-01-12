package com.utility.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO for user registration and authentication requests.
 * Includes validation constraints to ensure data integrity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    private String passWord;
}