package bjl.domain.model.percentage;


import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.user.User;

import java.math.BigDecimal;

/**
 * 抽成实体类
 * Created by zhangjin on 2017/8/14.
 */
public class Percentage extends ConcurrencySafeEntity {

    private User parent;    //上级用户
    private User user;      //下级用户
    private BigDecimal money;  //充值金额
    private BigDecimal agentRatio;  //提成比例
    private BigDecimal percentage;   //提成金额

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getAgentRatio() {
        return agentRatio;
    }

    public void setAgentRatio(BigDecimal agentRatio) {
        this.agentRatio = agentRatio;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }


    public Percentage() {
    }

    public Percentage(User parent, User user, BigDecimal money, BigDecimal agentRatio, BigDecimal percentage) {
        this.parent = parent;
        this.user = user;
        this.money = money;
        this.agentRatio = agentRatio;
        this.percentage = percentage;
    }
}
