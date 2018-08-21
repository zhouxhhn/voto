package bjl.domain.model.agent;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.user.User;

import java.math.BigDecimal;

/**
 * 代理配置
 * Created by zhangjin on 2018/1/15.
 */
public class AgentConfig extends ConcurrencySafeEntity {

    private User user;
    private BigDecimal valueR; //R值
    private Integer level; //代理等级
    private BigDecimal highRatio; //最高占比
    private BigDecimal factRatio;   //实际占比
    private Integer subCount;  //下级人数
    private BigDecimal restScore; //剩余分


//    private BigDecimal totalGrantScore; //总授权分
//    private BigDecimal alreadyGrantScore; //已授权分
//    private BigDecimal restGrantScore; //剩余授权分
//    private BigDecimal frozenGrantScore; //冻结分数


    public BigDecimal getRestScore() {
        return restScore;
    }

    public void setRestScore(BigDecimal restScore) {
        this.restScore = restScore;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getValueR() {
        return valueR;
    }

    public void setValueR(BigDecimal valueR) {
        this.valueR = valueR;
    }

    public BigDecimal getHighRatio() {
        return highRatio;
    }

    public void setHighRatio(BigDecimal highRatio) {
        this.highRatio = highRatio;
    }

    public BigDecimal getFactRatio() {
        return factRatio;
    }

    public void setFactRatio(BigDecimal factRatio) {
        this.factRatio = factRatio;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

//    public BigDecimal getTotalGrantScore() {
//        return totalGrantScore;
//    }
//
//    public void setTotalGrantScore(BigDecimal totalGrantScore) {
//        this.totalGrantScore = totalGrantScore;
//    }
//
//    public BigDecimal getAlreadyGrantScore() {
//        return alreadyGrantScore;
//    }
//
//    public void setAlreadyGrantScore(BigDecimal alreadyGrantScore) {
//        this.alreadyGrantScore = alreadyGrantScore;
//    }
//
//    public BigDecimal getRestGrantScore() {
//        return restGrantScore;
//    }
//
//    public void setRestGrantScore(BigDecimal restGrantScore) {
//        this.restGrantScore = restGrantScore;
//    }
//
//    public BigDecimal getFrozenGrantScore() {
//        return frozenGrantScore;
//    }
//
//    public void setFrozenGrantScore(BigDecimal frozenGrantScore) {
//        this.frozenGrantScore = frozenGrantScore;
//    }
}
