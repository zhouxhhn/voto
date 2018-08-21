package bjl.application.agent.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/18
 */
public class EditAgentConfigCommand {

    private String id;
    private BigDecimal valueR; //R值
    private BigDecimal highRatio; //最高占比
    private BigDecimal factRatio;   //实际占比
    private BigDecimal companyFact;
    private BigDecimal totalGrantScore; //总授权分
    private BigDecimal alreadyGrantScore; //已授权分
    private BigDecimal restGrantScore; //剩余授权分
    private BigDecimal frozenGrantScore; //冻结分数

    public BigDecimal getCompanyFact() {
        return companyFact;
    }

    public void setCompanyFact(BigDecimal companyFact) {
        this.companyFact = companyFact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getTotalGrantScore() {
        return totalGrantScore;
    }

    public void setTotalGrantScore(BigDecimal totalGrantScore) {
        this.totalGrantScore = totalGrantScore;
    }

    public BigDecimal getAlreadyGrantScore() {
        return alreadyGrantScore;
    }

    public void setAlreadyGrantScore(BigDecimal alreadyGrantScore) {
        this.alreadyGrantScore = alreadyGrantScore;
    }

    public BigDecimal getRestGrantScore() {
        return restGrantScore;
    }

    public void setRestGrantScore(BigDecimal restGrantScore) {
        this.restGrantScore = restGrantScore;
    }

    public BigDecimal getFrozenGrantScore() {
        return frozenGrantScore;
    }

    public void setFrozenGrantScore(BigDecimal frozenGrantScore) {
        this.frozenGrantScore = frozenGrantScore;
    }
}
