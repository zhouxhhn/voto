package bjl.application.scoredetailed.command;


import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/12/26.
 */
public class CreateScoreDetailedCommand {

    private String userId;
    private BigDecimal score;
    private Integer actionType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public CreateScoreDetailedCommand() {
    }

    public CreateScoreDetailedCommand(String userId, BigDecimal score, Integer actionType) {
        this.userId = userId;
        this.score = score;
        this.actionType = actionType;
    }
}
