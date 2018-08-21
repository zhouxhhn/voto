package bjl.domain.model.profitrecord;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * Created by zhangjin on 2018/1/30
 */
public class ProfitRecord extends ConcurrencySafeEntity {

    private Integer init; //重置初始分 0 成 1失败
    private Integer profit; //收益计算 0成功 1失败

    public Integer getInit() {
        return init;
    }

    public void setInit(Integer init) {
        this.init = init;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }
}
