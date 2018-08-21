package bjl.core.exception;

/**
 * 参数错误
 * <p>
 * Created by pengyi on 2016/3/3.
 */
public class ParameterException extends RuntimeException {

    public ParameterException() {
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

}
