package bjl.application.agent.reprensentation;


import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/3/1
 */
public class AgentRepresentation {

    private String parentId;
    private String id;
    private String name;
    private String username;

    private String level; //代理等级
    private BigDecimal highRatio; //最高占比
    private BigDecimal firstRatio;   //上级占比
    private BigDecimal secondRatio; //下级占比
    private BigDecimal R;   //玩家R值
    private Integer subCount;  //下级人数
    private BigDecimal restScore; //剩余分

    public BigDecimal getFirstRatio() {
        return firstRatio;
    }

    public void setFirstRatio(BigDecimal firstRatio) {
        this.firstRatio = firstRatio;
    }

    public BigDecimal getSecondRatio() {
        return secondRatio;
    }

    public void setSecondRatio(BigDecimal secondRatio) {
        this.secondRatio = secondRatio;
    }

    public BigDecimal getR() {
        return R;
    }

    public void setR(BigDecimal r) {
        R = r;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public BigDecimal getRestScore() {
        return restScore;
    }

    public void setRestScore(BigDecimal restScore) {
        this.restScore = restScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getHighRatio() {
        return highRatio;
    }

    public void setHighRatio(BigDecimal highRatio) {
        this.highRatio = highRatio;
    }


    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

}
