package bjl.application.spreadprofit.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/5/3
 */
public class ListSpreadProfitCommand extends BasicPaginationCommand {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
