package bjl.domain.model.pay;

import bjl.core.util.CoreDateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjin on 2017/11/23.
 */
public class JuXinTongPay {

    private String pay_memberid;        //商户ID
    private String pay_orderid;         //商户订单号
    private String pay_amount;      //订单金额，元，两位小数
    private String pay_applydate;       //订单提交时间 2014-12-26 18:18:18
    private String pay_bankcode;        //银行
    private String pay_notifyurl;       //异步通知地址
    private String pay_callbackurl;     //同步回调地址
    private String pay_reserved1;       //扩展地址1
    private String pay_md5sign;         //签名字段

    public String getPay_memberid() {
        return pay_memberid;
    }

    public void setPay_memberid(String pay_memberid) {
        this.pay_memberid = pay_memberid;
    }

    public String getPay_orderid() {
        return pay_orderid;
    }

    public void setPay_orderid(String pay_orderid) {
        this.pay_orderid = pay_orderid;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPay_applydate() {
        return pay_applydate;
    }

    public void setPay_applydate(String pay_applydate) {
        this.pay_applydate = pay_applydate;
    }

    public String getPay_bankcode() {
        return pay_bankcode;
    }

    public void setPay_bankcode(String pay_bankcode) {
        this.pay_bankcode = pay_bankcode;
    }

    public String getPay_notifyurl() {
        return pay_notifyurl;
    }

    public void setPay_notifyurl(String pay_notifyurl) {
        this.pay_notifyurl = pay_notifyurl;
    }

    public String getPay_callbackurl() {
        return pay_callbackurl;
    }

    public void setPay_callbackurl(String pay_callbackurl) {
        this.pay_callbackurl = pay_callbackurl;
    }

    public String getPay_reserved1() {
        return pay_reserved1;
    }

    public void setPay_reserved1(String pay_reserved1) {
        this.pay_reserved1 = pay_reserved1;
    }

    public String getPay_md5sign() {
        return pay_md5sign;
    }

    public void setPay_md5sign(String pay_md5sign) {
        this.pay_md5sign = pay_md5sign;
    }

    public JuXinTongPay(String pay_orderid, BigDecimal pay_amount, String pay_bankcode, String pay_reserved1) {
        Map<String,Object> fields = new HashMap<>();
        this.pay_memberid = "10038";
        fields.put("pay_memberid",this.pay_memberid);
        this.pay_orderid = pay_orderid;
        fields.put("pay_orderid",this.pay_orderid);
        this.pay_amount = pay_amount.setScale(2,RoundingMode.HALF_DOWN).toString();
        fields.put("pay_amount",this.pay_amount);
        this.pay_applydate = CoreDateUtils.formatDateTime(new Date());
        fields.put("pay_applydate",this.pay_applydate);
        this.pay_bankcode = pay_bankcode;
        fields.put("pay_bankcode",this.pay_bankcode);
        this.pay_notifyurl = "http://wanhao.zzr06.com:8090/api/recharge/cr_notify_jxt";
        fields.put("pay_notifyurl",this.pay_notifyurl);
        this.pay_callbackurl = "http://wanhao.zzr06.com:8090/api/recharge/success";
        fields.put("pay_callbackurl",this.pay_callbackurl);
        this.pay_reserved1 = pay_reserved1;
        this.pay_md5sign = MerPay.toSign("rIyaePKWlMpbiFazOpxl5AHDdB3ngx",fields) ;
    }

}
