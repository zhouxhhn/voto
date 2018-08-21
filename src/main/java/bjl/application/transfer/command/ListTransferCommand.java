package bjl.application.transfer.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2017/12/26.
 */
public class ListTransferCommand extends BasicPaginationCommand {

    private String transferName; //转账人
    private String receiveName; //收款人
    private String startDate;
    private String endDate;
    private Integer status; //状态

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
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
