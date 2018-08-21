package bjl.application.spreadprofit.command;

import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;


/**
 * Created by zhangjin on 2018/4/16
 */
public class SpreadProfitCommand {

    private Account account;
    private Long totalBet;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(Long totalBet) {
        this.totalBet = totalBet;
    }

    public SpreadProfitCommand() {
    }

    public SpreadProfitCommand(Object[] objects) {
        User user = (User) objects[1];
        this.account = user.getAccount();
        this.totalBet = (Long) objects[0];
    }
}
