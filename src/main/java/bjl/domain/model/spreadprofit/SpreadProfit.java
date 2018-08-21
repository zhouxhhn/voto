package bjl.domain.model.spreadprofit;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * 推广收益
 * Created by zhangjin on 2018/4/12
 */
public class SpreadProfit extends ConcurrencySafeEntity {

    private Account account;
    private BigDecimal yesterdayProfit; //昨日推广收益
    private BigDecimal weekProfit; //本周推广收益
    private BigDecimal lastWeekProfit; //上周推广收益
    private BigDecimal monthProfit; //本月推广收益
    private BigDecimal totalProfit; //历史总收益
    private BigDecimal unsettledProfit; //累计未结算收益
    private BigDecimal receiveProfit;   //可领取收益
    private BigDecimal todayProfit; //本日收益

    public BigDecimal getTodayProfit() {
        return todayProfit;
    }

    public void setTodayProfit(BigDecimal todayProfit) {
        this.todayProfit = todayProfit;
    }

    public BigDecimal getWeekProfit() {
        return weekProfit;
    }

    public void setWeekProfit(BigDecimal weekProfit) {
        this.weekProfit = weekProfit;
    }

    public BigDecimal getLastWeekProfit() {
        return lastWeekProfit;
    }

    public void setLastWeekProfit(BigDecimal lastWeekProfit) {
        this.lastWeekProfit = lastWeekProfit;
    }

    public BigDecimal getMonthProfit() {
        return monthProfit;
    }

    public void setMonthProfit(BigDecimal monthProfit) {
        this.monthProfit = monthProfit;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getYesterdayProfit() {
        return yesterdayProfit;
    }

    public void setYesterdayProfit(BigDecimal yesterdayProfit) {
        this.yesterdayProfit = yesterdayProfit;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getUnsettledProfit() {
        return unsettledProfit;
    }

    public void setUnsettledProfit(BigDecimal unsettledProfit) {
        this.unsettledProfit = unsettledProfit;
    }

    public BigDecimal getReceiveProfit() {
        return receiveProfit;
    }

    public void setReceiveProfit(BigDecimal receiveProfit) {
        this.receiveProfit = receiveProfit;
    }
}
