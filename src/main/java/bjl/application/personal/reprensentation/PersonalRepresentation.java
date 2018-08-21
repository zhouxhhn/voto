package bjl.application.personal.reprensentation;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class PersonalRepresentation {

    private String name;
    private Integer boots;
    private Integer games;
    private BigDecimal playerPro;
    private BigDecimal bankerPro;
    private BigDecimal playerPir;
    private BigDecimal bankerPir;
    private BigDecimal drawPro;
    private BigDecimal bankPlayLose;
    private BigDecimal bankPlayProfit;
    private BigDecimal triratnaLose;
    private BigDecimal triratnaProfit;
    private BigDecimal effective;

    public Integer getBoots() {
        return boots;
    }

    public void setBoots(Integer boots) {
        this.boots = boots;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPlayerPro() {
        return playerPro;
    }

    public void setPlayerPro(BigDecimal playerPro) {
        this.playerPro = playerPro;
    }

    public BigDecimal getBankerPro() {
        return bankerPro;
    }

    public void setBankerPro(BigDecimal bankerPro) {
        this.bankerPro = bankerPro;
    }

    public BigDecimal getPlayerPir() {
        return playerPir;
    }

    public void setPlayerPir(BigDecimal playerPir) {
        this.playerPir = playerPir;
    }

    public BigDecimal getBankerPir() {
        return bankerPir;
    }

    public void setBankerPir(BigDecimal bankerPir) {
        this.bankerPir = bankerPir;
    }

    public BigDecimal getDrawPro() {
        return drawPro;
    }

    public void setDrawPro(BigDecimal drawPro) {
        this.drawPro = drawPro;
    }

    public BigDecimal getBankPlayLose() {
        return bankPlayLose;
    }

    public void setBankPlayLose(BigDecimal bankPlayLose) {
        this.bankPlayLose = bankPlayLose;
    }

    public BigDecimal getBankPlayProfit() {
        return bankPlayProfit;
    }

    public void setBankPlayProfit(BigDecimal bankPlayProfit) {
        this.bankPlayProfit = bankPlayProfit;
    }

    public BigDecimal getTriratnaLose() {
        return triratnaLose;
    }

    public void setTriratnaLose(BigDecimal triratnaLose) {
        this.triratnaLose = triratnaLose;
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
}
