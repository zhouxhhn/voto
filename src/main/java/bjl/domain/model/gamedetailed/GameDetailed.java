package bjl.domain.model.gamedetailed;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.user.User;

import java.math.BigDecimal;

/**
 * 玩家下注明细
 * Created by zhangjin on 2017/12/26.
 */
public class GameDetailed extends ConcurrencySafeEntity {

    private User user;      //用户
    private Integer hallType;   //大厅类型  1.澳门厅  2.菲律宾厅 3.越南厅
    private Integer games; //局数
    private Integer boots; //鞋数
    private Integer[] lottery; //开奖结果
    private BigDecimal player; //闲
    private BigDecimal banker; //庄
    private BigDecimal playerPair; //闲对
    private BigDecimal bankPair;//庄对
    private BigDecimal draw; //和局
    private BigDecimal restScore; //剩余分
    private BigDecimal initScore; //初始分
    private BigDecimal bankPlayProfit; //庄闲盈亏
    private BigDecimal triratnaProfit; //三宝盈亏
    private BigDecimal effective; //有效流水
    private BigDecimal bankPlayLose; //庄闲洗码
    private BigDecimal triratnaLose; //三宝洗码


    public BigDecimal getRestScore() {
        return restScore;
    }

    public void setRestScore(BigDecimal restScore) {
        this.restScore = restScore;
    }

    public BigDecimal getInitScore() {
        return initScore;
    }

    public void setInitScore(BigDecimal initScore) {
        this.initScore = initScore;
    }

    public Integer[] getLottery() {
        return lottery;
    }

    public void setLottery(Integer[] lottery) {
        this.lottery = lottery;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    public Integer getBoots() {
        return boots;
    }

    public void setBoots(Integer boots) {
        this.boots = boots;
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

}
