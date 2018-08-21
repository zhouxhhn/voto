package bjl.domain.model.user;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * 用户
 * Created by pengyi on 2016/4/15.
 */
public class User extends ConcurrencySafeEntity {

    private Account account;        //1.账户
    private BigDecimal score;       //2.当前积分
    private BigDecimal bankScore;  //3.保险箱积分
    private String playerAlias;//4.选手别名
    private String  agentAlias;//5.代理别名
    private Integer  printScreen ;// 6.1 显示 2 影藏
    private BigDecimal  primeScore;//7.初始分
    private BigDecimal dateScore;//8.日积分
    private BigDecimal  totalScore;//9.总积分
    private BigDecimal  bankerPlayerProportion;//10.庄闲占成
    private BigDecimal  bankerPlayerCredit;//11.庄闲占成额度
    private BigDecimal  triratnaProportion;//12.三宝占成
    private BigDecimal triratnaCredit;//13.三宝占成额度
    private BigDecimal bankerPlayerMix;//14.庄闲最小
    private BigDecimal  bankerPlayerMax;//15.庄闲最大
    private BigDecimal triratnaMix;//16.三宝最小
    private BigDecimal triratnaMax;//17.三包最大
    private Integer setTop;//18.1.置顶 2.不置顶
    private Integer virtual;//19.1.虚拟 2.不虚拟
    private Integer serial;//玩家序号

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Integer getSetTop() {
        return setTop;
    }

    public void setSetTop(Integer setTop) {
        this.setTop = setTop;
    }

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getBankScore() {
        return bankScore;
    }

    public void setBankScore(BigDecimal bankScore) {
        this.bankScore = bankScore;
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

    public Integer getPrintScreen() {
        return printScreen;
    }

    public void setPrintScreen(Integer printScreen) {
        this.printScreen = printScreen;
    }

    public BigDecimal getPrimeScore() {
        return primeScore;
    }

    public void setPrimeScore(BigDecimal primeScore) {
        this.primeScore = primeScore;
    }

    public BigDecimal getDateScore() {
        return dateScore;
    }

    public void setDateScore(BigDecimal dateScore) {
        this.dateScore = dateScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
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
