package exception;

/**
 * Custom exception for external API errors (e.g., Judge0)
 */
public class ApiException extends RuntimeException {

    private int statusCode;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}