package bjl.domain.model.pay;


import java.math.BigDecimal;

/**
 * 支付结果异步通知参数实体类
 * Created by zhangjin on 2017/9/8.
 */
public class PayNotify {

    private String orderNo;  //订单号
    private BigDecimal orderMoney; //订单金额

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }
}
