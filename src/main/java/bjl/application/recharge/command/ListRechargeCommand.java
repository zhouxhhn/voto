package bjl.application.recharge.command;

import bjl.core.common.BasicPaginationCommand;
import bjl.core.enums.YesOrNoStatus;

/**
 * Created by pengyi
 * Date : 16-7-19.
 */
public class ListRechargeCommand extends BasicPaginationCommand {

    private String name;
    private YesOrNoStatus isSuccess;
    private String startDate;
    private String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YesOrNoStatus getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(YesOrNoStatus isSuccess) {
        this.isSuccess = isSuccess;
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
