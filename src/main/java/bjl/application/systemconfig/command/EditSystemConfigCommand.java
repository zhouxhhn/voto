package bjl.application.systemconfig.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/17
 */
public class EditSystemConfigCommand {

    private BigDecimal playerR;     //玩家R数
    private BigDecimal primeScore;  //玩家初始分
    private BigDecimal bankerPlayerMix; // 庄闲最小限红
    private BigDecimal bankerPlayerMax; // 庄闲最大限红
    private BigDecimal triratnaMix; // 三宝最小限红
    private BigDecimal triratnaMax; //三包最大限红
    private BigDecimal rechargeRatio;//充值比例
    private BigDecimal rechargeFee; //充值手续费
    private BigDecimal upScoreFee; //上分手续费
    private BigDecimal pump_1;  //一级返利比
    private BigDecimal pump_2;  //二级返利比
    private BigDecimal pump_3;  //三级返利比
    private BigDecimal pump_4;  //四级返利比
    private BigDecimal pump_5;  //五级返利比
    private Integer openPump; //是否开启返利活动

    public BigDecimal getPump_1() {
        return pump_1;
    }

    public void setPump_1(BigDecimal pump_1) {
        this.pump_1 = pump_1;
    }

    public BigDecimal getPump_2() {
        return pump_2;
    }

    public void setPump_2(BigDecimal pump_2) {
        this.pump_2 = pump_2;
    }

    public BigDecimal getPump_3() {
        return pump_3;
    }

    public void setPump_3(BigDecimal pump_3) {
        this.pump_3 = pump_3;
    }

    public BigDecimal getPump_4() {
        return pump_4;
    }

    public void setPump_4(BigDecimal pump_4) {
        this.pump_4 = pump_4;
    }

    public BigDecimal getPump_5() {
        return pump_5;
    }

    public void setPump_5(BigDecimal pump_5) {
        this.pump_5 = pump_5;
    }

    public Integer getOpenPump() {
        return openPump;
    }

    public void setOpenPump(Integer openPump) {
        this.openPump = openPump;
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public BigDecimal getUpScoreFee() {
        return upScoreFee;
    }

    public void setUpScoreFee(BigDecimal upScoreFee) {
        this.upScoreFee = upScoreFee;
    }

    public BigDecimal getRechargeRatio() {
        return rechargeRatio;
    }

    public void setRechargeRatio(BigDecimal rechargeRatio) {
        this.rechargeRatio = rechargeRatio;
    }

    public BigDecimal getPlayerR() {
        return playerR;
    }

    public void setPlayerR(BigDecimal playerR) {
        this.playerR = playerR;
    }

    public BigDecimal getPrimeScore() {
        return primeScore;
    }

    public void setPrimeScore(BigDecimal primeScore) {
        this.primeScore = primeScore;
    }

    public BigDecimal getBankerPlayerMix() {
        return bankerPlayerMix;
    }

    public void setBankerPlayerMix(BigDecimal bankerPlayerMix) {
        this.bankerPlayerMix = bankerPlayerMix;
    }

    public BigDecimal getBankerPlayerMax() {
        return bankerPlayerMax;
    }

    public void setBankerPlayerMax(BigDecimal bankerPlayerMax) {
        this.bankerPlayerMax = bankerPlayerMax;
    }

    public BigDecimal getTriratnaMix() {
        return triratnaMix;
    }

    public void setTriratnaMix(BigDecimal triratnaMix) {
        this.triratnaMix = triratnaMix;
    }

    public BigDecimal getTriratnaMax() {
        return triratnaMax;
    }

    public void setTriratnaMax(BigDecimal triratnaMax) {
        this.triratnaMax = triratnaMax;
    }
}
