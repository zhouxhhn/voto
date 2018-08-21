package bjl.core.api;

/**
 * Created by pengyi on 2016/4/15.
 */
public class ApiResponse<T> {

    private ApiReturnCode code;     // API执行结果

    private T data;           //返回数据

    public ApiReturnCode getCode() {
        return code;
    }

    public void setCode(ApiReturnCode code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiResponse() {
    }

    public ApiResponse(ApiReturnCode code) {
        this(code, null);
    }

    public ApiResponse(ApiReturnCode code, T data) {
        this.code = code;
        this.data = data;
    }


}
