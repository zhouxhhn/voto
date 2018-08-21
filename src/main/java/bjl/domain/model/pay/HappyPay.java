package bjl.domain.model.pay;

import bjl.core.enums.PayType;

/**
 * Created by zhangjin on 2017/11/2.
 */
public class HappyPay {

    private String saruLruid;   //商户号
    private String out_trade_no;//订单号
    private Integer transAmt;//支付金额
    private String notify_url;//通知地址
    private String body;        //商品信息
    private String frpcode; //交易类型
    private String sign;    //签名

    public String getSaruLruid() {
        return saruLruid;
    }

    public void setSaruLruid(String saruLruid) {
        this.saruLruid = saruLruid;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Integer transAmt) {
        this.transAmt = transAmt;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrpcode() {
        return frpcode;
    }

    public void setFrpcode(String frpcode) {
        this.frpcode = frpcode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public HappyPay(String out_trade_no, Integer transAmt, String body, PayType payType) {
        this.saruLruid = "6000000159";
        this.out_trade_no = out_trade_no;
        this.transAmt = transAmt;
        this.notify_url = "http://wanhao.zzr06.com:8090/api/recharge/cr_notify_happy";
        this.body = body;
        if(payType == PayType.ALIPAY){
            this.frpcode = "ALIPAY_NATIVE";
        }else if(payType == PayType.WECHAT){
            this.frpcode = "WEIXIN_NATIVE";
        }
    }
}
