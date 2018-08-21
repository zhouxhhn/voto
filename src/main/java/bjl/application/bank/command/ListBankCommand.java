package bjl.application.bank.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2017/9/7.
 */
public class ListBankCommand extends BasicPaginationCommand {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
