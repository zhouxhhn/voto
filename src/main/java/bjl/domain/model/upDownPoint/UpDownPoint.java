package bjl.domain.model.upDownPoint;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dyp on 2017-12-22.
 */
public class UpDownPoint  extends ConcurrencySafeEntity {

    private String name;//玩家昵称
    private String userName;
    private Date createDate;//创建时间
    private BigDecimal upDownPoint;//上下分
    private String operationUser;//操作的人
    private Integer upDownPointType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUpDownPointType() {
        return upDownPointType;
    }

    public void setUpDownPointType(Integer upDownPointType) {
        this.upDownPointType = upDownPointType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
