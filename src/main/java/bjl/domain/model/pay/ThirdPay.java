package bjl.domain.model.pay;


import bjl.core.enums.PayType;
import bjl.core.util.MD5;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 3vpay支付
 * Created by zhangjin on 2017/9/7.
 */
public class ThirdPay {

    private String appId;       //产品ID
    private String partnerId;   //商户ID
    private String imsi;        //安卓 imsi 或者 ios版本号
    private String deviceCod;   //设备号(安卓 imei ，ios32 位字符串)
    private String channelOrderId;//商户订单 ID(32个字符以内)
    private int platform;    //客户端平台（0 ios,1 安卓）
    private String body;        //商品名称
    private String detail;      //详细描述
    private int totalFee;       //商品金额,分为单位
    private String attach;      //透传字段，支付通知里原样返回
    private int payType;     //支付类型：1300微信WAP
    private String timeStamp;   //时间戳，13位长度
    private String sign;        //签名
    private String notifyUrl;   //支付完成服务端通知回调 url
    private String returnUrl;   //支付完成后显示的界面 url
    private String clientIp;    //客户端实际外网IP，支付宝用
    private String spUserId;    //支付用户 id，唯一性，必 须为纯数字，商户自己定 义
    private String bankSegment; //银行代号


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getDeviceCod() {
        return deviceCod;
    }

    public void setDeviceCod(String deviceCod) {
        this.deviceCod = deviceCod;
    }

    public String getChannelOrderId() {
        return channelOrderId;
    }

    public void setChannelOrderId(String channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSpUserId() {
        return spUserId;
    }

    public void setSpUserId(String spUserId) {
        this.spUserId = spUserId;
    }

    public String getBankSegment() {
        return bankSegment;
    }

    public void setBankSegment(String bankSegment) {
        this.bankSegment = bankSegment;
    }

    public ThirdPay() {
    }

    public ThirdPay(String imsi, String deviceCod, String channelOrderId, int platform, int totalFee, String attach,
                    PayType payType, String clientIp,String spUserId, String bankSegment) {
        try {
            if(PayType.WECHAT == payType){
                this.appId = "1009";
            } else if (PayType.ALIPAY == payType){
                this.appId = "1002";
            } else if (PayType.BANK == payType){
                this.appId = "1003";
            } else if (PayType.QQPAYCODE == payType){
                this.appId = "1004";
            } else if (PayType.QQPAY == payType){
                this.appId = "1005";
            } else if (PayType.ALIPAYCODE == payType){
                this.appId = "1006";
            } else if (PayType.WECHATCODE == payType){
                this.appId = "1007";
            }
            this.partnerId = "100509";
            this.imsi = imsi;
            this.deviceCod = deviceCod;
            this.channelOrderId = channelOrderId;
            this.platform = platform;
            this.body = "充值";
            this.detail = "";
            this.totalFee = totalFee*100;
            this.attach = attach;
            if(PayType.WECHAT == payType){
                this.payType = 1300;
            } else if (PayType.ALIPAY == payType){
                this.payType = 2000;
            } else if (PayType.BANK == payType){
                this.payType = 2100;
            }else if (PayType.QQPAYCODE == payType){
                this.payType = 2400;
            } else if (PayType.QQPAY == payType){
                this.payType = 2600;
            } else if (PayType.ALIPAYCODE == payType){
                this.payType = 1800;
            } else if (PayType.WECHATCODE == payType){
                this.payType = 1400;
            }
            this.timeStamp = String.valueOf(new Date().getTime());
            this.notifyUrl = "http://wanhao.zzr06.com:8090/api/recharge/cr_notify";
            this.returnUrl = "http://wanhao.zzr06.com:8090/api/recharge/success";
            this.clientIp = clientIp;
            this.spUserId = spUserId ;
            this.bankSegment = bankSegment;
            String signString = "appId=" + this.appId + "&timeStamp=" + this.timeStamp + "&totalFee=" + this.totalFee + "&key=";
            if(this.payType == 1300){
                signString = signString+"75f20ddff66f5ae01497804f85a5020f";
            }else if(this.payType == 2000){
                signString = signString+"cdf0aefa19fd9aa87fc5f1484b8f4bb9";
            }else if(this.payType == 2100){
                signString = signString+"676564f7118172439ed2190409b756b0";
            }else if(this.payType == 2400){
                signString = signString+"62a4d9f70a1b536b599cd1554437962c";
            }else if(this.payType == 2600){
                signString = signString+"2c09e4f8245d974d3fd2d4f629d552ed";
            }else if(this.payType == 1800){
                signString = signString+"80602164bb7e4f86302cbb322ee57a8d";
            }else if(this.payType == 1400){
                signString = signString+"cbe54293156c312afe369d9583199a44";
            }
            System.out.println("签名前的字符串："+signString);
            this.sign = new MD5().getMD5ofStr(signString.getBytes("utf-8")).toUpperCase();
            System.out.println("签名后的字符串："+this.sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String urlParam() {
        //微信
        String ulrStr = "appId=" + this.appId + "&partnerId=" + this.partnerId + "&imsi=" + this.imsi + "&deviceCod=" + this.deviceCod +
                "&channelOrderId=" + this.channelOrderId + "&platform=" + this.platform + "&body=" + this.body + "&detail=" + this.detail +
                "&totalFee=" + this.totalFee + "&attach=" + this.attach + "&payType=" + this.payType + "&timeStamp=" + this.timeStamp +
                "&sign=" + this.sign + "&notifyUrl=" + this.notifyUrl + "&returnUrl=" + this.returnUrl;
        if (this.payType == 2000) { //支付宝
            ulrStr = ulrStr + "&clientIp=" + this.clientIp;
        } else if (this.payType == 2100) { //网银
            ulrStr = ulrStr + "&spUserId=" + this.spUserId + "&bankSegment=" + this.bankSegment;
        } else if(this.payType == 2400 || this.payType == 1400){
             ulrStr ="appId=" + this.appId + "&partnerId=" + this.partnerId + "&imsi=" + this.imsi + "&deviceCod=" + this.deviceCod +
                     "&channelOrderId=" + this.channelOrderId + "&platform=" + this.platform + "&body=" + this.body + "&detail=" + this.detail +
                     "&totalFee=" + this.totalFee + "&attach=" + this.attach + "&payType=" + this.payType + "&timeStamp=" + this.timeStamp +
                     "&sign=" + this.sign + "&notifyUrl=" + this.notifyUrl;
        }
        System.out.println("支付请求参数:"+ulrStr);
        return ulrStr;
    }
}
