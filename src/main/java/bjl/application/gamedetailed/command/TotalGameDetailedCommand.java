package bjl.application.gamedetailed.command;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/8.
 */
public class TotalGameDetailedCommand {

    private BigDecimal player; //闲
    private BigDecimal banker; //庄
    private BigDecimal playerPair; //闲对
    private BigDecimal bankPair;//庄对
    private BigDecimal draw; //和局
    private BigDecimal bankPlayProfit; //庄闲盈亏
    private BigDecimal triratnaProfit; //三宝盈亏
    private BigDecimal effective; //有效流水
    private BigDecimal bankPlayLose; //庄闲洗码
    private BigDecimal triratnaLose; //三宝洗码

    public BigDecimal getPlayer() {
        return player;
    }

    public void setPlayer(BigDecimal player) {
        this.player = player;
    }

    public BigDecimal getBanker() {
        return banker;
    }

    public void setBanker(BigDecimal banker) {
        this.banker = banker;
    }

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

    public BigDecimal getBankPlayProfit() {
        return bankPlayProfit;
    }

    public void setBankPlayProfit(BigDecimal bankPlayProfit) {
        this.bankPlayProfit = bankPlayProfit;
    }

    public BigDecimal getTriratnaProfit() {
        return triratnaProfit;
    }

    public void setTriratnaProfit(BigDecimal triratnaProfit) {
        this.triratnaProfit = triratnaProfit;
    }

    public BigDecimal getEffective() {
        return effective;
    }

    public void setEffective(BigDecimal effective) {
        this.effective = effective;
    }

    public BigDecimal getBankPlayLose() {
        return bankPlayLose;
    }

    public void setBankPlayLose(BigDecimal bankPlayLose) {
        this.bankPlayLose = bankPlayLose;
    }

    public BigDecimal getTriratnaLose() {
        return triratnaLose;
    }

    public void setTriratnaLose(BigDecimal triratnaLose) {
        this.triratnaLose = triratnaLose;
    }

    public TotalGameDetailedCommand() {

    }

    public TotalGameDetailedCommand(List list) {

        Object[] objects = (Object[]) list.get(0);
        if(objects != null){
            this.player = (BigDecimal) objects[0];
            this.banker = (BigDecimal)objects[1];
            this.playerPair = (BigDecimal)objects[2];
            this.bankPair =(BigDecimal) objects[3];
            this.draw = (BigDecimal)objects[4];
            this.bankPlayProfit = (BigDecimal)objects[5];
            this.triratnaProfit = (BigDecimal)objects[6];
            this.effective = (BigDecimal)objects[7];
            this.bankPlayLose = (BigDecimal)objects[8];
            this.triratnaLose = (BigDecimal)objects[9];
        }else {
            BigDecimal bigDecimal = BigDecimal.valueOf(0);
            this.player = bigDecimal;
            this.banker = bigDecimal;
            this.playerPair = bigDecimal;
            this.bankPair = bigDecimal;
            this.draw = bigDecimal;
            this.bankPlayProfit = bigDecimal;
            this.triratnaProfit = bigDecimal;
            this.effective = bigDecimal;
            this.bankPlayLose = bigDecimal;
            this.triratnaLose = bigDecimal;
        }

    }
}
