package bjl.application.agent.reprensentation;


import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class AgentConfigRepresentation {

    private String id;
    private String name;
    private String alias; //别名
    private String level; //代理等级
    private BigDecimal valueR; //R值
    private BigDecimal highRatio; //最高占比
    private BigDecimal factRatio;   //实际占比
    private BigDecimal firstFact;
    private BigDecimal secondFact;
    private BigDecimal companyFact;
    private Integer subCount;  //下级人数
    private BigDecimal restScore; //剩余分

    public BigDecimal getCompanyFact() {
        return companyFact;
    }

    public void setCompanyFact(BigDecimal companyFact) {
        this.companyFact = companyFact;
    }

    public BigDecimal getFirstFact() {
        return firstFact;
    }

    public void setFirstFact(BigDecimal firstFact) {
        this.firstFact = firstFact;
    }

    public BigDecimal getSecondFact() {
        return secondFact;
    }

    public void setSecondFact(BigDecimal secondFact) {
        this.secondFact = secondFact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getRestScore() {
        return restScore;
    }

    public void setRestScore(BigDecimal restScore) {
        this.restScore = restScore;
    }
}
