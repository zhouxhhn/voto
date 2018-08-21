package bjl.application.logger.command;

import bjl.core.enums.LoggerType;

/**
 * Author pengyi
 * Date 17-4-21.
 */
public class CreateLoggerCommand {
    private String userId;
    private LoggerType loggerType;
    private String loggerContent;
    private String ip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public CreateLoggerCommand(String userId, LoggerType loggerType, String loggerContent, String ip) {
        this.userId = userId;
        this.loggerType = loggerType;
        this.loggerContent = loggerContent;
        this.ip = ip;
    }
}
