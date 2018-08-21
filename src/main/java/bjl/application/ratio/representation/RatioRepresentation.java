package bjl.application.ratio.representation;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/3/30
 */
public class RatioRepresentation {

    private String id;
    private BigDecimal userR; //玩家R值
    private BigDecimal secondFact; //二级实际占比
    private BigDecimal firstFact;  //一级实际占比
    private BigDecimal companyFact; //公司占比

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

    public BigDecimal getUserR() {
        return userR;
    }

    public void setUserR(BigDecimal userR) {
        this.userR = userR;
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
