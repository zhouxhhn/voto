package bjl.core.exception;

/**
 * Created by pengyi on 2016/4/19.
 */
public class PasswordAuthException extends RuntimeException {

    public PasswordAuthException() {
    }

    public PasswordAuthException(String message) {
        super(message);
    }

    public PasswordAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordAuthException(Throwable cause) {
        super(cause);
    }

}
