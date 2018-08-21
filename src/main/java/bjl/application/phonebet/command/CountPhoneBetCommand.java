package bjl.application.phonebet.command;

import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/18
 */
public class CountPhoneBetCommand {

    private BigDecimal loseScore; //洗码
    private Account account;

    public BigDecimal getLoseScore() {
        return loseScore;
    }

    public void setLoseScore(BigDecimal loseScore) {
        this.loseScore = loseScore;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CountPhoneBetCommand(Object[] objects) {

        this.loseScore = (BigDecimal) objects[0];
        this.account = (Account) objects[1];
    }
}
