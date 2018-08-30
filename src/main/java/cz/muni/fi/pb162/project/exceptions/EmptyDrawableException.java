package cz.muni.fi.pb162.project.exceptions;

/**
 * @author Stefan Ockay
 */
public class EmptyDrawableException extends Exception {
    private String message;
    private Throwable cause = null;

    public EmptyDrawableException(String message) {
        this.message = message;
    }

    public EmptyDrawableException(String message, Throwable cause) {
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
