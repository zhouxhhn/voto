package bjl.domain.model.agent;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;
import com.sun.org.apache.bcel.internal.generic.BIPUSH;

import java.math.BigDecimal;

/**
 * 代理收益
 * Created by zhangjin on 2018/1/12.
 */
public class AgentProfit extends ConcurrencySafeEntity {

    private Account play;  //玩家
    private Integer place; //开工点  1 微投 2 电投
    private Account firstAgent; //一级代理
    private Account secondAgent;//二级代理
    private BigDecimal loseScore; //转码数
    private BigDecimal intervalScore; //上下数   剩余分-初始分
    private BigDecimal supRatio; //上级占比
    private BigDecimal subRatio; //下级占比
    private BigDecimal subHigh; //下级最高
    private BigDecimal supHigh; //上级最高
    private BigDecimal comRatio; //公司占比
    private BigDecimal addScore; //上分
    private BigDecimal rechargeScore; //充值分数

    private BigDecimal supPay; // 上级付出
    private BigDecimal subPay; //下级付出

    private BigDecimal supIncome; //上级收入
    private BigDecimal subIncome; //下级收入
    private BigDecimal comIncome; //公司收入
    private BigDecimal returnScore; //返水
    private BigDecimal discount; //打折转码
    private BigDecimal subGrain; //下级所得码粮
    private BigDecimal supGrain; // 上级所得码粮
    private BigDecimal comGrain; //公司应付码粮
    private BigDecimal firstFee; //一级手续费
    private BigDecimal secondFee; //二级手续费
    private BigDecimal comFee;  //公司手续费
    private BigDecimal subBalance; //下级余额
    private BigDecimal supBalance; //上级余额
    private BigDecimal playBalance; //玩家余额
    private BigDecimal playR;  //玩家R
    private BigDecimal subR;   //下级R
    private BigDecimal supR;  //上级R

    public BigDecimal getSubHigh() {
        return subHigh;
    }

    public void setSubHigh(BigDecimal subHigh) {
        this.subHigh = subHigh;
    }

    public BigDecimal getSupHigh() {
        return supHigh;
    }

    public void setSupHigh(BigDecimal supHigh) {
        this.supHigh = supHigh;
    }

    public BigDecimal getPlayR() {
        return playR;
    }

    public void setPlayR(BigDecimal playR) {
        this.playR = playR;
    }

    public BigDecimal getSubR() {
        return subR;
    }

    public void setSubR(BigDecimal subR) {
        this.subR = subR;
    }

    public BigDecimal getSupR() {
        return supR;
    }

    public void setSupR(BigDecimal supR) {
        this.supR = supR;
    }

    public BigDecimal getComRatio() {
        return comRatio;
    }

    public void setComRatio(BigDecimal comRatio) {
        this.comRatio = comRatio;
    }

    public BigDecimal getComIncome() {
        return comIncome;
    }

    public void setComIncome(BigDecimal comIncome) {
        this.comIncome = comIncome;
    }

    public BigDecimal getComGrain() {
        return comGrain;
    }

    public void setComGrain(BigDecimal comGrain) {
        this.comGrain = comGrain;
    }

    public BigDecimal getComFee() {
        return comFee;
    }

    public void setComFee(BigDecimal comFee) {
        this.comFee = comFee;
    }

    public BigDecimal getSubBalance() {
        return subBalance;
    }

    public void setSubBalance(BigDecimal subBalance) {
        this.subBalance = subBalance;
    }

    public BigDecimal getSupBalance() {
        return supBalance;
    }

    public void setSupBalance(BigDecimal supBalance) {
        this.supBalance = supBalance;
    }

    public BigDecimal getPlayBalance() {
        return playBalance;
    }

    public void setPlayBalance(BigDecimal playBalance) {
        this.playBalance = playBalance;
    }

    public BigDecimal getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    public BigDecimal getSecondFee() {
        return secondFee;
    }

    public void setSecondFee(BigDecimal secondFee) {
        this.secondFee = secondFee;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public BigDecimal getSubGrain() {
        return subGrain;
    }

    public void setSubGrain(BigDecimal subGrain) {
        this.subGrain = subGrain;
    }

    public BigDecimal getSupGrain() {
        return supGrain;
    }

    public void setSupGrain(BigDecimal supGrain) {
        this.supGrain = supGrain;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getReturnScore() {
        return returnScore;
    }

    public void setReturnScore(BigDecimal returnScore) {
        this.returnScore = returnScore;
    }

    public Account getPlay() {
        return play;
    }

    public void setPlay(Account play) {
        this.play = play;
    }

    public Account getFirstAgent() {
        return firstAgent;
    }

    public void setFirstAgent(Account firstAgent) {
        this.firstAgent = firstAgent;
    }

    public Account getSecondAgent() {
        return secondAgent;
    }

    public void setSecondAgent(Account secondAgent) {
        this.secondAgent = secondAgent;
    }

    public BigDecimal getLoseScore() {
        return loseScore;
    }

    public void setLoseScore(BigDecimal loseScore) {
        this.loseScore = loseScore;
    }

    public BigDecimal getIntervalScore() {
        return intervalScore;
    }

    public void setIntervalScore(BigDecimal intervalScore) {
        this.intervalScore = intervalScore;
    }

    public BigDecimal getSupRatio() {
        return supRatio;
    }

    public void setSupRatio(BigDecimal supRatio) {
        this.supRatio = supRatio;
    }

    public BigDecimal getSubRatio() {
        return subRatio;
    }

    public void setSubRatio(BigDecimal subRatio) {
        this.subRatio = subRatio;
    }

    public BigDecimal getAddScore() {
        return addScore;
    }

    public void setAddScore(BigDecimal addScore) {
        this.addScore = addScore;
    }

    public BigDecimal getRechargeScore() {
        return rechargeScore;
    }

    public void setRechargeScore(BigDecimal rechargeScore) {
        this.rechargeScore = rechargeScore;
    }

    public BigDecimal getSupPay() {
        return supPay;
    }

    public void setSupPay(BigDecimal supPay) {
        this.supPay = supPay;
    }

    public BigDecimal getSubPay() {
        return subPay;
    }

    public void setSubPay(BigDecimal subPay) {
        this.subPay = subPay;
    }

    public BigDecimal getSupIncome() {
        return supIncome;
    }

    public void setSupIncome(BigDecimal supIncome) {
        this.supIncome = supIncome;
    }

    public BigDecimal getSubIncome() {
        return subIncome;
    }

    public void setSubIncome(BigDecimal subIncome) {
        this.subIncome = subIncome;
    }
}
