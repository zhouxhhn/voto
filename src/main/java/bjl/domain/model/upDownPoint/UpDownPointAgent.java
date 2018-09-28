package bjl.domain.model.upDownPoint;

import java.math.BigDecimal;
import java.util.Date;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * Created by dyp on 2017-12-22.
 */
public class UpDownPointAgent extends UpDownPoint {

    private String company;//公司
    private String firtAngent;//一级代理
    private String refree;//推荐人

    public String getRefree() {
        return refree;
    }

    public void setRefree(String refree) {
        this.refree = refree;
    }

    public String getFirtAngent() {
        return firtAngent;
    }

    public void setFirtAngent(String firtAngent) {
        this.firtAngent = firtAngent;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


}
