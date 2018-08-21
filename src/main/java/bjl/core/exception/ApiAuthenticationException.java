package bjl.core.exception;

/**
 * Api鉴权失败
 * Created by pengyi on 2016/4/15.
 */
public class ApiAuthenticationException extends Exception {

    public ApiAuthenticationException() {
    }

    public ApiAuthenticationException(String message) {
        super(message);
    }

    public ApiAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiAuthenticationException(Throwable cause) {
        super(cause);
    }

}
