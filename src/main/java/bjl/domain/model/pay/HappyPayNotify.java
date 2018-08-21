package bjl.domain.model.pay;

import bjl.core.util.MD5Util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhangjin on 2017/11/2.
 */
public class HappyPayNotify {

    private String status;//状态
    private String result_code;//业务结果
    private String out_trade_no;//订单号
    private String total_fee;//支付金额
    private String sign;    //签名

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean checkSign(){
        try {
            Map<String,Object> map = new TreeMap<>();
            Class cls = this.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field field: fields){
                field.setAccessible(true);
                if(field.get(this) != null && !"sign".equals(field.getName())){
                    map.put(field.getName(),field.get(this));
                }
            }
            String signStr = MD5Util.MD5(map+"0c3dff48d3e84bf3af13e3cdd78DRFTY").toUpperCase();
            System.out.println("生成的签名："+signStr);
            if(signStr != null){
                return signStr.equals(this.sign);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
