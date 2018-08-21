package bjl.application.ratio.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/5/2
 */
public class EditRatioCommand {

    private String id;
    private BigDecimal userR;
    private BigDecimal firstFact;
    private BigDecimal secondFact;
    private BigDecimal companyFact;

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
}
