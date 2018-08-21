package bjl.application.triratna.command;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class TotalTriratna {

    private BigDecimal playerPair; //闲对
    private BigDecimal bankPair;//庄对
    private BigDecimal draw; //和局
    private BigDecimal triratnaProfit; //三宝盈亏

    public BigDecimal getPlayerPair() {
        return playerPair;
    }

    public void setPlayerPair(BigDecimal playerPair) {
        this.playerPair = playerPair;
    }

    public BigDecimal getBankPair() {
        return bankPair;
    }

    public void setBankPair(BigDecimal bankPair) {
        this.bankPair = bankPair;
    }

    public BigDecimal getDraw() {
        return draw;
    }

    public void setDraw(BigDecimal draw) {
        this.draw = draw;
    }

    public BigDecimal getTriratnaProfit() {
        return triratnaProfit;
    }

    public void setTriratnaProfit(BigDecimal triratnaProfit) {
        this.triratnaProfit = triratnaProfit;
    }

    public TotalTriratna(List list) {
        Object[] objects = (Object[]) list.get(0);
        if(objects != null){

            this.bankPair = objects[0] == null ? BigDecimal.valueOf(0) : (BigDecimal)objects[0];
            this.playerPair = objects[1] == null ? BigDecimal.valueOf(0) : (BigDecimal)objects[1];
            this.draw = objects[2] == null ? BigDecimal.valueOf(0) : (BigDecimal)objects[2];
            this.triratnaProfit = objects[3] == null ? BigDecimal.valueOf(0) : (BigDecimal)objects[3];
        }else {
            BigDecimal bigDecimal = BigDecimal.valueOf(0);
            this.bankPair = bigDecimal;
            this.playerPair = bigDecimal;
            this.draw = bigDecimal;
            this.triratnaProfit = bigDecimal;
        }
    }
}
