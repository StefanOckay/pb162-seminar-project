package cz.muni.fi.pb162.project.exceptions;

/**
 * @author Stefan Ockay
 */

public class MissingVerticesException extends Exception {
    private String message;
    private Throwable cause = null;

    public MissingVerticesException(String message) {
        this.message = message;
    }

    public MissingVerticesException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
