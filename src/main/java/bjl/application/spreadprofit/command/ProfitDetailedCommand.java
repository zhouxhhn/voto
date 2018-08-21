package bjl.application.spreadprofit.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/4/17
 */
public class ProfitDetailedCommand {

    private String name;  //昵称
    private String level; //等级
    private BigDecimal bet; //下注金额
    private BigDecimal profit; //贡献收益

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public void setBet(BigDecimal bet) {
        this.bet = bet;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public ProfitDetailedCommand(Object object) {
        Object[] objects = (Object[]) object;
        this.name = (String) objects[0];
        this.level = (String) objects[1];
        this.bet = (BigDecimal) objects[2];
    }
}
