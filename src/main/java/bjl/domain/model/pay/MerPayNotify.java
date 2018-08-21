package bjl.domain.model.pay;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjin on 2017/10/23.
 */
public class MerPayNotify {

    private Integer returnCode;     //支付结果，固定值。0 代表成功，1代表失败
    private String returnMsg;       //返回信息
    private String resultCode;      //业务结果 0：成功、1：失败
    private String errCode;         //错误代码
    private String errCodeDes;      //错误代码描述
    private String channel;         //支付类型
    private String mchId;           //商户号
    private String outTradeNo;      //订单号
    private String outChannelNo;    //上游渠道订单号
    private String body;            //商品描述
    private BigDecimal amount;          //交易金额
    private String transTime;       //交易时间
    private String status;          //订单状态 01：未支付 02：已支付 05：转入退款 09：支付失败
    private String outRefundNo;     //商户退款订单号
    private String channelRefundNo; //上游渠道退款单号
    private String sign;            //签名
    private String outRefundTime;   //退款时间
    private String refundStatus;    //退款状态

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutChannelNo() {
        return outChannelNo;
    }

    public void setOutChannelNo(String outChannelNo) {
        this.outChannelNo = outChannelNo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getChannelRefundNo() {
        return channelRefundNo;
    }

    public void setChannelRefundNo(String channelRefundNo) {
        this.channelRefundNo = channelRefundNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutRefundTime() {
        return outRefundTime;
    }

    public void setOutRefundTime(String outRefundTime) {
        this.outRefundTime = outRefundTime;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public boolean checkSign(){
        try {
            Map<String,Object> map = new HashMap<>();
            Class cls = this.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field field: fields){
                field.setAccessible(true);
                if(field.get(this) != null && !"sign".equals(field.getName())){
                    map.put(field.getName(),field.get(this));
                }
            }
            String signStr = MerPay.toSign("4efb90a78ca84a1e903a1bba76034800",map);
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
