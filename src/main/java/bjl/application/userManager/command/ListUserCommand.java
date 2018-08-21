package bjl.application.userManager.command;

import bjl.core.common.BasicPaginationCommand;
import bjl.core.enums.EnableStatus;
import bjl.domain.model.account.Account;
/**
 * Created by dyp on 2017-12-14.
 */
public class ListUserCommand extends BasicPaginationCommand {
    private String userName;
    private EnableStatus status;
    private String startDate;  //开始时间
    private String endDate;  //结束时间
    private Account account;        //1.账户
    private String  agentAlias;//5.代理别名

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAgentAlias() {
        return agentAlias;
    }

    public void setAgentAlias(String agentAlias) {
        this.agentAlias = agentAlias;
    }
}
