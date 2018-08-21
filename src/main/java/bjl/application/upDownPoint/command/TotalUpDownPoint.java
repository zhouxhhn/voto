package bjl.application.upDownPoint.command;

import java.math.BigDecimal;

/**
 * Created by dyp on 2018-1-15.
 */
//总计上下分
public class TotalUpDownPoint {
    private BigDecimal upPoint;//上分
    private  BigDecimal downPoint;//下分

    public BigDecimal getUpPoint() {
        return upPoint;
    }

    public void setUpPoint(BigDecimal upPoint) {
        this.upPoint = upPoint;
    }

    public BigDecimal getDownPoint() {
        return downPoint;
    }

    public void setDownPoint(BigDecimal downPoint) {
        this.downPoint = downPoint;
    }
}
