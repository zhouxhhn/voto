package bjl.websocket.command;


import bjl.application.ratio.representation.RatioRepresentation;
import bjl.core.util.ServiceUtil;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.ratio.Ratio;
import bjl.domain.model.robot.Robot;
import bjl.domain.model.user.User;
import bjl.tcp.GlobalVariable;

import java.math.BigDecimal;

/**
 * WebSocket 消息体
 * Created by zhangjin on 2017/12/25.
 */
public class WSMessage {

    private String username;
    private String name;
    private BigDecimal[] score;
    private BigDecimal[] total;
    private Integer hide; //是否隐藏 1隐藏 2不隐藏
    private Integer virtual;//是否虚拟 1虚拟 2不虚拟
    private BigDecimal ratio; //占比
    private BigDecimal tRatio; //三宝占比
    private BigDecimal bankMesa; //庄台面分
    private BigDecimal playMesa; //闲台面分
    private BigDecimal thisProfit; //本次盈利

    public BigDecimal getThisProfit() {
        return thisProfit;
    }

    public void setThisProfit(BigDecimal thisProfit) {
        this.thisProfit = thisProfit;
    }

    public BigDecimal gettRatio() {
        return tRatio;
    }

    public void settRatio(BigDecimal tRatio) {
        this.tRatio = tRatio;
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

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
    }

    public BigDecimal[] getTotal() {
        return total;
    }

    public void setTotal(BigDecimal[] total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal[] getScore() {
        return score;
    }

    public void setScore(BigDecimal[] score) {
        this.score = score;
    }

    public WSMessage(String username, BigDecimal player, BigDecimal bank, BigDecimal playerPair, BigDecimal bankPair, BigDecimal draw,
                     BigDecimal point, BigDecimal restScore, BigDecimal initScore) {
        this.username = username;
        this.score = new BigDecimal[]{player,bank,playerPair,bankPair,draw,point,restScore,initScore};
    }

    public WSMessage(String username,User user,String type,String score) {
        this.username = username;
        this.name = user.getAccount().getName();
        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),BigDecimal.valueOf(0),user.getScore(),user.getPrimeScore()};
        switch (type){
            case "1": bigDecimals[0] = BigDecimal.valueOf(Integer.valueOf(score));
                break;
            case "2": bigDecimals[4] = BigDecimal.valueOf(Integer.valueOf(score));
                break;
            case "3": bigDecimals[1] = BigDecimal.valueOf(Integer.valueOf(score));
                break;
            case "4": bigDecimals[2] = BigDecimal.valueOf(Integer.valueOf(score));
                break;
            case "5": bigDecimals[3] = BigDecimal.valueOf(Integer.valueOf(score));
                break;
            case "6": bigDecimals[2] = BigDecimal.valueOf(Integer.valueOf(score));
                      bigDecimals[3] = BigDecimal.valueOf(Integer.valueOf(score));
                break;
            case "7": bigDecimals[2] = BigDecimal.valueOf(Integer.valueOf(score));
                      bigDecimals[3] = BigDecimal.valueOf(Integer.valueOf(score));
                      bigDecimals[4] = BigDecimal.valueOf(Integer.valueOf(score));
        }
        this.score = bigDecimals;
        this.hide = user.getPrintScreen();
        this.virtual = user.getVirtual();
        Account parent = ServiceUtil.serviceUtil.getAgentAppService().getAgent(user.getAccount());
        AgentConfig parentConfig = ServiceUtil.serviceUtil.getAgentConfigAppService().getByAccount(parent);
        RatioRepresentation ratio = ServiceUtil.serviceUtil.getRatioAppService().getByAccount(user.getAccount().getId());
        BigDecimal bigDecimal = BigDecimal.valueOf(0);

        if(parentConfig != null && ratio != null){
            if(parentConfig.getLevel() == 2){
                bigDecimal = bigDecimal.add(ratio.getSecondFact() != null ? ratio.getSecondFact() : BigDecimal.valueOf(0));
                bigDecimal = bigDecimal.add(ratio.getFirstFact() != null ? ratio.getFirstFact() : BigDecimal.valueOf(0));
            } else {
                bigDecimal = bigDecimal.add(ratio.getFirstFact()!= null ? ratio.getFirstFact() : BigDecimal.valueOf(0));
            }
        }

        this.ratio = bigDecimal.divide(BigDecimal.valueOf(100));
        this.tRatio = bigDecimal.divide(BigDecimal.valueOf(100));
        this.bankMesa = BigDecimal.valueOf(0);
        this.playMesa = BigDecimal.valueOf(0);
    }

    /**
     * 机器人
     */
    public WSMessage(Robot robot, Integer type, Integer score) {
        this.username = robot.getId();
        this.name = robot.getName();
        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),BigDecimal.valueOf(0),robot.getScore(),robot.getPrimeScore()};
        switch (type){
            case 1: bigDecimals[0] = BigDecimal.valueOf(score);
                break;
            case 2: bigDecimals[4] = BigDecimal.valueOf(score);
                break;
            case 3: bigDecimals[1] = BigDecimal.valueOf(score);
                break;
            case 4: bigDecimals[2] = BigDecimal.valueOf(score);
                break;
            case 5: bigDecimals[3] = BigDecimal.valueOf(score);
                break;
            case 6: bigDecimals[2] = BigDecimal.valueOf(score);
                bigDecimals[3] = BigDecimal.valueOf(score);
                break;
            case 7: bigDecimals[2] = BigDecimal.valueOf(score);
                bigDecimals[3] = BigDecimal.valueOf(score);
                bigDecimals[4] = BigDecimal.valueOf(score);
        }
        this.score = bigDecimals;
        this.hide = 1;
        this.virtual = 3;

        BigDecimal bigDecimal = BigDecimal.valueOf(0);

        this.ratio = bigDecimal;
        this.tRatio = bigDecimal;
        this.bankMesa = bigDecimal;
        this.playMesa = bigDecimal;
    }
}
