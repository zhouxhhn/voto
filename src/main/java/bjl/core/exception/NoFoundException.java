package bjl.core.exception;

/**
 * Created by pengyi on 2016/3/4.
 */
public class NoFoundException extends RuntimeException {

    public NoFoundException() {
    }

    public NoFoundException(String message) {
        super(message);
    }

    public NoFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFoundException(Throwable cause) {
        super(cause);
    }

}
