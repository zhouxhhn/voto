package bjl.domain.model.pay;

import bjl.core.enums.PayType;
import bjl.core.util.MD5;
import bjl.core.util.MD5Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mer支付
 * Created by zhangjin on 2017/10/23.
 */
public class MerPay  {

    private String tradeType;   //固定 pay.submit
    private String version;     //版本号、固定1.7
    private String channel;     //支付类型
    private String mchId;       //商户号
    private String sign;        //签名
    private String body;        //描述
    private String outTradeNo;  //商户订单号
    private BigDecimal amount;  //交易金额
    private String notifyUrl;   //异步通知地址
    private String callbackUrl; //同步回调地址


    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public MerPay(PayType payType,String outTradeNo, BigDecimal amount, String body) {
        Map<String,Object> fields = new HashMap<>();
        this.tradeType = "pay.submit";
        fields.put("tradeType",this.tradeType);
        this.version = "1.7";
        fields.put("version",this.version);
        if(payType == PayType.WECHAT){
            this.channel = "wxH5";
        }else {
            this.channel = "alipayH5";
        }
        fields.put("channel",this.channel);
        this.mchId = "0000100100000000003";
        fields.put("mchId",this.mchId);
        this.body = body;
        fields.put("body",this.body);
        this.outTradeNo = outTradeNo;
        fields.put("outTradeNo",this.outTradeNo);
        this.amount = amount;
        fields.put("amount",this.amount.toString());
        this.notifyUrl = "http://wanhao.zzr06.com:8090/api/recharge/cr_notify_mer";
        fields.put("notifyUrl",this.notifyUrl);
        this.callbackUrl = "http://wanhao.zzr06.com:8090/api/recharge/success";
        fields.put("callbackUrl",this.callbackUrl);
        //拼接字符串
        this.sign = toSign("4efb90a78ca84a1e903a1bba76034800",fields);

    }

    public static String toSign(String secret , Map<String,Object> fields){
        try{
            //按参数名asscic码排序
            List<String> list = new ArrayList<>();
            list.addAll(fields.keySet());
            java.util.Collections.sort(list);
            String strSign = "";
            for(String key : list){
                strSign += key+"="+fields.get(key)+"&";
            }
            strSign += "key="+secret;
            System.out.println("签名字符串："+strSign);
            return MD5Util.MD5(strSign).toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
