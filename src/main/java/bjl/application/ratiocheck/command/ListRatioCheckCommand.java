package bjl.application.ratiocheck.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/3/1
 */
public class ListRatioCheckCommand extends BasicPaginationCommand{

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
