package bjl.core.exception;

/**
 * Created by zhangjin on 2017/7/3.
 */

public class CountNotEnoughException extends RuntimeException {

    public CountNotEnoughException() {
    }

    public CountNotEnoughException(String message) {
        super(message);
    }

    public CountNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountNotEnoughException(Throwable cause) {
        super(cause);
    }

}
