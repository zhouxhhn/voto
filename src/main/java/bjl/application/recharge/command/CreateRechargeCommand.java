package bjl.application.recharge.command;

import bjl.core.enums.PayType;

import java.math.BigDecimal;

/**
 * Created by pengyi on 2016/4/11.
 */
public class CreateRechargeCommand {

    private BigDecimal money;
    private String userId;
    private PayType payType;
    private Integer pass;
    private String clientIp;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }
}

