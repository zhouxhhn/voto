package bjl.application.userManager.command;

import java.math.BigDecimal;

/**
 * Created by dyp on 2017-12-15.
 */
public class CreateUserCommand {

    private String id;        //   id
    private String playerAlias;//4.选手别名
    private String  agentAlias;//5.代理别名
    private BigDecimal upDownScore;  //上下分数
    private BigDecimal  bankerPlayerProportion;//10.庄闲占成
    private BigDecimal  bankerPlayerCredit;//11.庄闲占成额度
    private BigDecimal  triratnaProportion;//12.三宝占成
    private BigDecimal triratnaCredit;//13.三宝占成额度
    private BigDecimal bankerPlayerMix;//14.庄闲最小
    private BigDecimal  bankerPlayerMax;//15.庄闲最大
    private BigDecimal triratnaMix;//16.三宝最小
    private BigDecimal triratnaMax;//17.三包最大


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerAlias() {
        return playerAlias;
    }

    public void setPlayerAlias(String playerAlias) {
        this.playerAlias = playerAlias;
    }

    public String getAgentAlias() {
        return agentAlias;
    }

    public void setAgentAlias(String agentAlias) {
        this.agentAlias = agentAlias;
    }

    public BigDecimal getUpDownScore() {
        return upDownScore;
    }

    public void setUpDownScore(BigDecimal upDownScore) {
        this.upDownScore = upDownScore;
    }

    public BigDecimal getBankerPlayerProportion() {
        return bankerPlayerProportion;
    }

    public void setBankerPlayerProportion(BigDecimal bankerPlayerProportion) {
        this.bankerPlayerProportion = bankerPlayerProportion;
    }

    public BigDecimal getBankerPlayerCredit() {
        return bankerPlayerCredit;
    }

    public void setBankerPlayerCredit(BigDecimal bankerPlayerCredit) {
        this.bankerPlayerCredit = bankerPlayerCredit;
    }

    public BigDecimal getTriratnaProportion() {
        return triratnaProportion;
    }

    public void setTriratnaProportion(BigDecimal triratnaProportion) {
        this.triratnaProportion = triratnaProportion;
    }

    public BigDecimal getTriratnaCredit() {
        return triratnaCredit;
    }

    public void setTriratnaCredit(BigDecimal triratnaCredit) {
        this.triratnaCredit = triratnaCredit;
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
