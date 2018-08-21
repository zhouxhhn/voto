package bjl.core.api;

/**
 * 返回消息格式
 * Created by zhangjin on 2017/8/28.
 */
public class ResultData {

    private boolean isSuccess;      //是否成功
    private ApiReturnCode returnCode;   //错误码
    private String data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ApiReturnCode getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(ApiReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResultData() {
    }

    public ResultData(boolean isSuccess, ApiReturnCode returnCode, String data) {
        this.isSuccess = isSuccess;
        this.returnCode = returnCode;
        this.data = data;
    }

    public ResultData(boolean isSuccess, String data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public ResultData(boolean isSuccess, ApiReturnCode returnCode) {
        this.isSuccess = isSuccess;
        this.returnCode = returnCode;
    }
}
