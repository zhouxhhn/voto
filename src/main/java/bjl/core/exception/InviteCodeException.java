package bjl.core.exception;

/**
 * Created by zhangjin on 2017/6/7.
 */
public class InviteCodeException extends RuntimeException {

    public InviteCodeException() {
    }

    public InviteCodeException(String message) {
        super(message);
    }

    public InviteCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InviteCodeException(Throwable cause) {
        super(cause);
    }

}
