package bjl.domain.model.recharge;

import bjl.core.enums.PayType;
import bjl.core.enums.YesOrNoStatus;
import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值
 * <p>
 * Created by pengyi on 16-7-9.
 */
public class Recharge extends ConcurrencySafeEntity {

    private String orderNo;      //订单号
    private Account account;        //用户
    private BigDecimal money;       //金额
    private YesOrNoStatus isSuccess;//是否成功
    private PayType payType;        //支付类型
    private Date payTime;
    private Integer pass;       //通道

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void changeIsSuccess(YesOrNoStatus isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void changePayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public YesOrNoStatus getIsSuccess() {
        return isSuccess;
    }

    public PayType getPayType() {
        return payType;
    }


    public Date getPayTime() {
        return payTime;
    }


    private void setMoney(BigDecimal money) {
        this.money = money;
    }

    private void setIsSuccess(YesOrNoStatus isSuccess) {
        this.isSuccess = isSuccess;
    }

    private void setPayType(PayType payType) {
        this.payType = payType;
    }


    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Recharge() {
        super();
    }

    public Recharge(Account account,String orderNo, BigDecimal money, YesOrNoStatus isSuccess, PayType payType, Integer pass) {
        this.account = account;
        this.orderNo = orderNo;
        this.money = money;
        this.isSuccess = isSuccess;
        this.payType = payType;
        this.setCreateDate(new Date());
        this.pass = pass;
    }
}
