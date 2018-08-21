package bjl.core.exception;

/**
 * Created by pengyi on 2016/5/26.
 */
public class AccountException extends RuntimeException {

    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

}
