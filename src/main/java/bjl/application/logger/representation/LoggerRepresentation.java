package bjl.application.logger.representation;

import bjl.core.enums.LoggerType;

import java.util.Date;

/**
 * Created by zhangjin on 2017/6/9.
 */
public class LoggerRepresentation {

    private String id;
    private Date createDate;
    private String userName;
    private LoggerType loggerType;
    private String loggerContent;
    private String ip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
