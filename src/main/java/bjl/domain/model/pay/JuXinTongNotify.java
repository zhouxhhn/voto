package bjl.domain.model.pay;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjin on 2017/11/24.
 */
public class JuXinTongNotify {

    private String memberid;
    private String orderid;
    private String amount;
    private String datetime;
    private String returncode;
    private String reserved1;
    private String sign;

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean checkSign(){
        try {
            Map<String,Object> map = new HashMap<>();
            Class cls = this.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field field: fields){
                field.setAccessible(true);
                if(field.get(this) != null && !"sign".equals(field.getName()) && !"reserved1".equals(field.getName())){
                    map.put(field.getName(),field.get(this));
                }
            }
            String signStr = MerPay.toSign("rIyaePKWlMpbiFazOpxl5AHDdB3ngx",map);
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
