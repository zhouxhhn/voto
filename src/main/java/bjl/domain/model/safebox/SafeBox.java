package bjl.domain.model.safebox;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * 保险箱实体类
 * Created by zhangjin on 2017/9/15.
 */
public class SafeBox extends ConcurrencySafeEntity {

    private Account account;      //用户
    private BigDecimal access;//存取金额
    private BigDecimal bankMoney;//保险箱金额
    private BigDecimal money;   //可用金额
    private BigDecimal totalMoney;//总金额

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAccess() {
        return access;
    }

    public void setAccess(BigDecimal access) {
        this.access = access;
    }

    public BigDecimal getBankMoney() {
        return bankMoney;
    }

    public void setBankMoney(BigDecimal bankMoney) {
        this.bankMoney = bankMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

}
