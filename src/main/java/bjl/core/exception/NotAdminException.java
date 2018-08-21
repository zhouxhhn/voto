package bjl.core.exception;

/**
 * Created by pengyi on 2016/5/26.
 */
public class NotAdminException extends RuntimeException {

    public NotAdminException() {
    }

    public NotAdminException(String message) {
        super(message);
    }

    public NotAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAdminException(Throwable cause) {
        super(cause);
    }

}
