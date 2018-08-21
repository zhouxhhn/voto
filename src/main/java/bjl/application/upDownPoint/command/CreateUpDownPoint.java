package bjl.application.upDownPoint.command;

import java.math.BigDecimal;

/**
 * Created by dyp on 2018-1-15.
 */
public class CreateUpDownPoint {
    private String id;//玩家
    private BigDecimal upDownPoint;//上下分数
    private String operationUser;//操作人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getUpDownPoint() {
        return upDownPoint;
    }

    public void setUpDownPoint(BigDecimal upDownPoint) {
        this.upDownPoint = upDownPoint;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }
}
