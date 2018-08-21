package bjl.domain.model.logger;

import bjl.core.enums.LoggerType;
import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;

import java.util.Date;

/**
 * Author pengyi
 * Date 17-4-21.
 */
public class Logger extends ConcurrencySafeEntity {

    private Account operationUser;                 //操作的人
    private LoggerType loggerType;              //日志类型
    private String loggerContent;               //日志内容
    private String ip;                          //ip

    public Account getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(Account operationUser) {
        this.operationUser = operationUser;
    }

    public LoggerType getLoggerType() {
        return loggerType;
    }

    public void setLoggerType(LoggerType loggerType) {
        this.loggerType = loggerType;
    }

    public String getLoggerContent() {
        return loggerContent;
    }

    public void setLoggerContent(String loggerContent) {
        this.loggerContent = loggerContent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Logger() {
        setCreateDate(new Date());
    }

    public Logger(Account account, LoggerType loggerType, String loggerContent, String ip) {
        this();
        this.operationUser = account;
        this.loggerType = loggerType;
        this.loggerContent = loggerContent;
        this.ip = ip;
    }
}
