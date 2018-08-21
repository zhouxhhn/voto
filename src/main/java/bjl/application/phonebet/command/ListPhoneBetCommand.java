package bjl.application.phonebet.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/1/9.
 */
public class ListPhoneBetCommand extends BasicPaginationCommand {

    private String name;
    private String startDate;
    private String endDate;
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
