package bjl.domain.model.recharge;

import bjl.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by apple on 2016/9/16.
 */
public class RechargeGive extends ConcurrencySafeEntity {

    private BigDecimal money;
    private BigDecimal giveMoney;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getGiveMoney() {
        return giveMoney;
    }

    public void setGiveMoney(BigDecimal giveMoney) {
        this.giveMoney = giveMoney;
    }

    public RechargeGive() {
        super();
    }

    public RechargeGive(BigDecimal money, BigDecimal giveMoney) {
        super();
        this.setCreateDate(new Date());
        this.money = money;
        this.giveMoney = giveMoney;
    }
}
