package bjl.application.triratna.representation;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class TriratnaRepresentation {

    private Integer boots;
    private Integer games;
    private BigDecimal player_pair;
    private BigDecimal bank_pair;//庄对
    private BigDecimal draw; //和局
    private BigDecimal triratna_profit;//三宝盈利
    private Object[] lottery; //开奖结果
    private Object count;

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }


    public Object getLottery() {
        return lottery;
    }

    public void setLottery(Object lottery) {
        int length = Array.getLength(lottery);
        Object[] os = new Object[length];
        for (int i = 0; i < os.length; i++) {
            os[i] = Array.get(lottery, i);
        }
        this.lottery = os;
    }

    public Integer getBoots() {
        return boots;
    }

    public void setBoots(Integer boots) {
        this.boots = boots;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    public BigDecimal getPlayer_pair() {
        return player_pair;
    }

    public void setPlayer_pair(BigDecimal player_pair) {
        this.player_pair = player_pair;
    }

    public BigDecimal getBank_pair() {
        return bank_pair;
    }

    public void setBank_pair(BigDecimal bank_pair) {
        this.bank_pair = bank_pair;
    }

    public BigDecimal getTriratna_profit() {
        return triratna_profit;
    }

    public void setTriratna_profit(BigDecimal triratna_profit) {
        this.triratna_profit = triratna_profit;
    }

    public BigDecimal getDraw() {
        return draw;
    }

    public void setDraw(BigDecimal draw) {
        this.draw = draw;
    }
}
