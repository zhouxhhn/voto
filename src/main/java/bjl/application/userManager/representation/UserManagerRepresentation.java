package bjl.application.userManager.representation;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dyp on 2018-1-12.
 */
public class UserManagerRepresentation {

    private String id;
    private Date createDate;  //注册时间
    private String name;
    private String firstAgent;
    private String secondAgent;
    private String referee;//推荐人
    private String playerAlias;//4.玩家别名
    private String  agentAlias;//5.代理别名
    private Integer  printScreen ;// 6.1 显示 2不显示
    private BigDecimal score;//当前积分
    private BigDecimal  primeScore;//7.初始分
    private BigDecimal bankScore;//银行分
    private BigDecimal dateScore; //昨日初始分
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
    private String token;
    private String username;

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public BigDecimal getDateScore() {
        return dateScore;
    }

    public void setDateScore(BigDecimal dateScore) {
        this.dateScore = dateScore;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getBankScore() {
        return bankScore;
    }

    public void setBankScore(BigDecimal bankScore) {
        this.bankScore = bankScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstAgent() {
        return firstAgent;
    }

    public void setFirstAgent(String firstAgent) {
        this.firstAgent = firstAgent;
    }

    public String getSecondAgent() {
        return secondAgent;
    }

    public void setSecondAgent(String secondAgent) {
        this.secondAgent = secondAgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getPrimeScore() {
        return primeScore;
    }

    public void setPrimeScore(BigDecimal primeScore) {
        this.primeScore = primeScore;
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
