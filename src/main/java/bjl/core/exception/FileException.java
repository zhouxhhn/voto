package bjl.core.exception;

/**
 * Created by zhangjin on 2017/9/16.
 */
public class FileException extends RuntimeException{

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
