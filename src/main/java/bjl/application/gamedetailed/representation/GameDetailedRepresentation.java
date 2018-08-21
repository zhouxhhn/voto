package bjl.application.gamedetailed.representation;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2018/1/6.
 */
public class GameDetailedRepresentation {

    private Date createDate; //创建时间
    private String xjNum; //靴局数
    private String name;      //用户
    private Integer hallType;   //大厅类型  1.澳门厅  2.菲律宾厅 3.越南厅
    private String result; //开奖结果
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getXjNum() {
        return xjNum;
    }

    public void setXjNum(String xjNum) {
        this.xjNum = xjNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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
}
