package bjl.domain.model.financialSummary;

import bjl.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dyp on 2018-1-10.
 */
public class FinancialSummary extends ConcurrencySafeEntity {


    private Integer boot;//靴
    private Integer bureau;//局
    private Integer hallType; //大厅类型
    private BigDecimal playerScore;//闲投注表分
    private BigDecimal bankerScore;//庄投注表分
    private BigDecimal playerProportion;//闲占成
    private BigDecimal bankerProportion;//庄占成
    private BigDecimal playerMesaScore;//闲台面分
    private BigDecimal bankerMesaScore;//庄台面分
    private String result;//开奖结果
    private BigDecimal mesaWinLoss;//台面分盈亏
    private BigDecimal mesaWashCode;//台面分洗码
    private BigDecimal zeroProfit ; //零数利润
    private BigDecimal  hedgeProfit ;  //对冲利润
    private BigDecimal   proportionProfit ; //占成利润
    private BigDecimal    profitSummary;//利润汇总

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }

    private String gameDetailedId;

    public String getGameDetailedId() {
        return gameDetailedId;
    }

    public void setGameDetailedId(String gameDetailedId) {
        this.gameDetailedId = gameDetailedId;
    }

    public Integer getBoot() {
        return boot;
    }

    public void setBoot(Integer boot) {
        this.boot = boot;
    }

    public Integer getBureau() {
        return bureau;
    }

    public void setBureau(Integer bureau) {
        this.bureau = bureau;
    }

    public BigDecimal getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(BigDecimal playerScore) {
        this.playerScore = playerScore;
    }

    public BigDecimal getBankerScore() {
        return bankerScore;
    }

    public void setBankerScore(BigDecimal bankerScore) {
        this.bankerScore = bankerScore;
    }

    public BigDecimal getPlayerProportion() {
        return playerProportion;
    }

    public void setPlayerProportion(BigDecimal playerProportion) {
        this.playerProportion = playerProportion;
    }

    public BigDecimal getBankerProportion() {
        return bankerProportion;
    }

    public void setBankerProportion(BigDecimal bankerProportion) {
        this.bankerProportion = bankerProportion;
    }

    public BigDecimal getPlayerMesaScore() {
        return playerMesaScore;
    }

    public void setPlayerMesaScore(BigDecimal playerMesaScore) {
        this.playerMesaScore = playerMesaScore;
    }

    public BigDecimal getBankerMesaScore() {
        return bankerMesaScore;
    }

    public void setBankerMesaScore(BigDecimal bankerMesaScore) {
        this.bankerMesaScore = bankerMesaScore;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BigDecimal getMesaWinLoss() {
        return mesaWinLoss;
    }

    public void setMesaWinLoss(BigDecimal mesaWinLoss) {
        this.mesaWinLoss = mesaWinLoss;
    }

    public BigDecimal getMesaWashCode() {
        return mesaWashCode;
    }

    public void setMesaWashCode(BigDecimal mesaWashCode) {
        this.mesaWashCode = mesaWashCode;
    }

    public BigDecimal getZeroProfit() {
        return zeroProfit;
    }

    public void setZeroProfit(BigDecimal zeroProfit) {
        this.zeroProfit = zeroProfit;
    }

    public BigDecimal getHedgeProfit() {
        return hedgeProfit;
    }

    public void setHedgeProfit(BigDecimal hedgeProfit) {
        this.hedgeProfit = hedgeProfit;
    }

    public BigDecimal getProportionProfit() {
        return proportionProfit;
    }

    public void setProportionProfit(BigDecimal proportionProfit) {
        this.proportionProfit = proportionProfit;
    }

    public BigDecimal getProfitSummary() {
        return profitSummary;
    }

    public void setProfitSummary(BigDecimal profitSummary) {
        this.profitSummary = profitSummary;
    }
}
