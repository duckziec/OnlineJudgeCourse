package utils;

import exception.ValidationException;

import java.util.regex.Pattern;

public class Validator {

    // Regex for a valid username: 3-20 characters, letters, numbers, underscore
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";
    // Standard regex for email validation
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    // Regex for a strong password
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    // Regex for a name: allows Unicode letters (for international names) and spaces
    private static final String NAME_REGEX = "^[\\p{L}\\s'-]+$";

    /**
     * Checks if a string value is not null or empty.
     */
    public static void validateRequired(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
    }

    /**
     * Validates a full name.
     */
    public static void validateName(String fieldName, String name) {
        validateRequired(fieldName, name);
        if (!Pattern.matches(NAME_REGEX, name)) {
            throw new ValidationException(fieldName + " must contain only letters and spaces.");
        }
    }

    /**
     * Validates a username.
     */
    public static void validateUsername(String username) {
        validateRequired("Username", username);
        if (!Pattern.matches(USERNAME_REGEX, username)) {
            throw new ValidationException("Username must be 3-20 characters and can only contain letters, numbers, and underscores.");
        }
    }

    /**
     * Validates an email address.
     */
    public static void validateEmail(String email) {
        validateRequired("Email", email);
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new ValidationException("Invalid email format.");
        }
    }

    /**
     * Validates a password for strength.
     */
    public static void validatePassword(String password) {
        validateRequired("Password", password);
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new ValidationException("Password must be at least 8 characters, including uppercase, lowercase, number, and special character.");
        }
    }

    /**
     * Validates if an integer is positive.
     */
    public static void validatePositiveInteger(String fieldName, int value) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be a positive integer.");
        }
    }
}
