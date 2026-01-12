package com.utility.auth.exception;

/**
 * Exception thrown when attempting to register a user that already exists.
 * <p>
 * This helps distinguish business logic errors (duplicate user) from other runtime issues.
 */
public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Ensures safe serialization in distributed systems

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserAlreadyExistsException with the specified cause.
     *
     * @param cause the underlying cause
     */
    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}