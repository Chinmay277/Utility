package com.utility.auth.exception;

/**
 * Custom exception thrown when user registration fails.
 * <p>
 * This exception wraps underlying causes (e.g., database errors, validation failures)
 * and provides a clear message for logging and client responses.
 */
public class UserRegistrationException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Ensures compatibility during serialization

    /**
     * Constructs a new UserRegistrationException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserRegistrationException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserRegistrationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause (e.g., SQLException, DataAccessException)
     */
    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserRegistrationException with the specified cause.
     *
     * @param cause the underlying cause
     */
    public UserRegistrationException(Throwable cause) {
        super(cause);
    }
}