package exception;

/**
 * Custom exception for validation errors
 */
public class ValidationException extends RuntimeException {

    private String field; // Field that failed validation

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}