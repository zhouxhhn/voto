package bjl.application.gamedetailed.command;

import bjl.domain.model.user.User;
import bjl.websocket.command.GameStatus;
import bjl.websocket.command.WSMessage;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/3.
 */
public class CreateGameDetailedCommand {

    private String user;      //用户
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
    private BigDecimal effective; //有效流水  (庄-闲)的绝对值 + 庄对 +闲对 +和
    private BigDecimal bankPlayLose; //庄闲洗码
    private BigDecimal triratnaLose; //三宝洗码
    private BigDecimal winTotal; //本局下注之后赢的钱
    private BigDecimal bankMesa; //庄台面分
    private BigDecimal playMesa; //闲台面分
    private Integer virtual; //虚拟

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
    }

    public BigDecimal getBankMesa() {
        return bankMesa;
    }

    public void setBankMesa(BigDecimal bankMesa) {
        this.bankMesa = bankMesa;
    }

    public BigDecimal getPlayMesa() {
        return playMesa;
    }

    public void setPlayMesa(BigDecimal playMesa) {
        this.playMesa = playMesa;
    }

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

    public BigDecimal getWinTotal() {
        return winTotal;
    }

    public void setWinTotal(BigDecimal winTotal) {
        this.winTotal = winTotal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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

    public Integer[] getLottery() {
        return lottery;
    }

    public void setLottery(Integer[] lottery) {
        this.lottery = lottery;
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

    public CreateGameDetailedCommand(WSMessage wsMessage, String[] strings, GameStatus gameStatus) {

        this.user = wsMessage.getUsername();
        this.hallType = Integer.valueOf(strings[0]);
        this.games = gameStatus.getjNum();
        this.boots = gameStatus.getxNum();
        Integer[] integers = new Integer[]{0,0,0,0,0};
        for(int i=2;i<strings.length;i++){
            integers[Integer.valueOf(strings[i])] = 1;
        }
        this.lottery = integers;
        this.player = wsMessage.getScore()[0];
        this.banker = wsMessage.getScore()[1];
        this.playerPair = wsMessage.getScore()[2];
        this.bankPair = wsMessage.getScore()[3];
        this.draw = wsMessage.getScore()[4];
        if(lottery[4] == 1){
            this.effective = this.playerPair.add(this.bankPair).add(this.draw);
        }else {
            this.effective = (this.banker.subtract(this.player)).abs().add(this.playerPair).add(this.bankPair).add(this.draw);
        }

        this.bankPlayProfit = BigDecimal.valueOf(0);
        this.triratnaProfit  = BigDecimal.valueOf(0);
        this.bankPlayLose = BigDecimal.valueOf(0);
        this.triratnaLose = BigDecimal.valueOf(0);
        this.winTotal = BigDecimal.valueOf(0);
        this.bankMesa = wsMessage.getScore()[1].multiply(wsMessage.getRatio()).setScale(0,BigDecimal.ROUND_HALF_UP);
        this.playMesa = wsMessage.getScore()[0].multiply(wsMessage.getRatio()).setScale(0,BigDecimal.ROUND_HALF_UP);
    }

}
