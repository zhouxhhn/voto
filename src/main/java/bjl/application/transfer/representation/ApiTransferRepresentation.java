package bjl.application.transfer.representation;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/12/26.
 */
public class ApiTransferRepresentation {

    private long time;
    private String userid;
    private BigDecimal gold;
    private BigDecimal balance;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
