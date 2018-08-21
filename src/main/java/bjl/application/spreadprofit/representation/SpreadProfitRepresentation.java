package bjl.application.spreadprofit.representation;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/5/3
 */
public class SpreadProfitRepresentation {

    private String name; //昵称
    private String userId;  //用户ID
    private Integer effective; //有效推广人数
    private Integer total; //总推广人数
    private BigDecimal yesterdayProfit; //昨日推广收益
    private BigDecimal weekProfit; //本周推广收益
    private BigDecimal lastWeekProfit; //上周推广收益
    private BigDecimal monthProfit; //本月推广收益
    private BigDecimal totalProfit; //历史总收益
    private BigDecimal unsettledProfit; //累计未结算收益
    private BigDecimal receiveProfit;   //可领取收益

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getYesterdayProfit() {
        return yesterdayProfit;
    }

    public void setYesterdayProfit(BigDecimal yesterdayProfit) {
        this.yesterdayProfit = yesterdayProfit;
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
