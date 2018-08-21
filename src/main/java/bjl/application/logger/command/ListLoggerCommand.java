package bjl.application.logger.command;

import bjl.core.common.BasicPaginationCommand;
import bjl.core.enums.LoggerType;

/**
 * Created by zhangjin on 2017/6/9.
 */
public class ListLoggerCommand extends BasicPaginationCommand {

    private String operationUser;
    private LoggerType loggerType;
    private String startDate;
    private String endDate;

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public LoggerType getLoggerType() {
        return loggerType;
    }

    public void setLoggerType(LoggerType loggerType) {
        this.loggerType = loggerType;
    }

    public String getStartDate() {
        return startDate;
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
}
