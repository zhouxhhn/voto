package bjl.application.recharge.repensentation;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/12/27.
 */
public class ApiRechargeRepresentation {

    private long time;
    private String action;
    private String status;
    private BigDecimal gold;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }


    public ApiRechargeRepresentation(long time, String action, String status, BigDecimal gold) {
        this.time = time;
        this.action = action;
        this.status = status;
        this.gold = gold;
    }
}
