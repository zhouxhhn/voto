package bjl.domain.model.withdraw;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2018/1/23
 */
public class Withdraw extends ConcurrencySafeEntity{

    private Account account;          //提现用户
    private BigDecimal money;   //申请金额
    private BigDecimal charge;  //手续费
    private BigDecimal actualMoney;//实提金额
    private Integer status;     //提现状态 1.待审核 2.允许提现 3.拒绝提现
    private String bankName;    //银行名称
    private String acc;         //到账账号
    private String comment;     //说明
    private Integer success;     //0.未处理 1.成功 2.失败 3.异常

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Withdraw() {
    }

    public Withdraw(Account account, BigDecimal money, BigDecimal charge, BigDecimal actualMoney, Integer status, String bankName, String acc, String comment, Integer success) {
        this.account = account;
        this.money = money;
        this.charge = charge;
        this.actualMoney = actualMoney;
        this.status = status;
        this.bankName = bankName;
        this.acc = acc;
        this.comment = comment;
        this.success = success;
        setCreateDate(new Date());
    }
}
