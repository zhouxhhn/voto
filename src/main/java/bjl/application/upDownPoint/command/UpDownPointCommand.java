package bjl.application.upDownPoint.command;

import bjl.core.common.BasicPaginationCommand;

import java.math.BigDecimal;


/**
 * Created by dyp on 2017-12-20.
 */
public class UpDownPointCommand extends BasicPaginationCommand{

    private String firstAgent;//一级代理昵称
    private String userName;//玩家昵称
    private Integer upDownPointType;//上下分类型1.上分 2.下分
    private String operationUser;//操作人
    private String startDate;  //开始时间
    private String endDate;

    public String getFirstAgent() {
        return firstAgent;
    }

    public void setFirstAgent(String firstAgent) {
        this.firstAgent = firstAgent;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

}
