package bjl.application.notice.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/1/17.
 */
public class ListNoticeCommand extends BasicPaginationCommand {

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
