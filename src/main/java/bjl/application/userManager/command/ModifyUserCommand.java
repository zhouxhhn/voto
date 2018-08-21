package bjl.application.userManager.command;

import bjl.application.shared.command.SharedCommand;

import java.math.BigDecimal;

/**
 * Created by dyp on 2017-12-18.
 */
public class ModifyUserCommand extends SharedCommand{

    private String id;
    private String playerAlias;//4.玩家别名
    private String  agentAlias;//5.代理别名
    private BigDecimal bankerPlayerProportion;//10.庄闲占成
    private BigDecimal  bankerPlayerCredit;//11.庄闲占成额度
    private BigDecimal  triratnaProportion;//12.三宝占成
    private BigDecimal triratnaCredit;//13.三宝占成额度
    private BigDecimal bankerPlayerMix;//14.庄闲最小
    private BigDecimal  bankerPlayerMax;//15.庄闲最大
    private BigDecimal triratnaMix;//16.三宝最小
    private BigDecimal triratnaMax;//17.三包最大
    private BigDecimal upDownPoint;//上下分数
    private Integer setTop;//置顶;
    private Integer serial;//玩家序号

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Integer getSetTop() {
        return setTop;
    }

    public void setSetTop(Integer setTop) {
        this.setTop = setTop;
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

    public BigDecimal getUpDownPoint() {
        return upDownPoint;
    }

    public void setUpDownPoint(BigDecimal upDownPoint) {
        this.upDownPoint = upDownPoint;
    }
}
