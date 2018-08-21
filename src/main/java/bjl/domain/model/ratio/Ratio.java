package bjl.domain.model.ratio;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/3/22
 */
public class Ratio extends ConcurrencySafeEntity {

    private Account account; //用户
    private BigDecimal secondFact; //二级实际占比
    private BigDecimal firstFact;  //一级实际占比
    private BigDecimal companyFact; //公司占比

    public BigDecimal getCompanyFact() {
        return companyFact;
    }

    public void setCompanyFact(BigDecimal companyFact) {
        this.companyFact = companyFact;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getSecondFact() {
        return secondFact;
    }

    public void setSecondFact(BigDecimal secondFact) {
        this.secondFact = secondFact;
    }

    public BigDecimal getFirstFact() {
        return firstFact;
    }

    public void setFirstFact(BigDecimal firstFact) {
        this.firstFact = firstFact;
    }
}
