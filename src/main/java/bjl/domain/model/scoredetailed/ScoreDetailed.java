package bjl.domain.model.scoredetailed;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.user.User;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/12/26.
 */
public class ScoreDetailed extends ConcurrencySafeEntity {

    private User user;  //用户
    private BigDecimal score;    //增减分数
    private BigDecimal newScore; //现有分数
    private Integer actionType;  //分数变动类型      3.电话投注冻结分数 4.充值 5.提现 6.转账 7.领取收益
    private Integer hallType; //大厅

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getNewScore() {
        return newScore;
    }

    public void setNewScore(BigDecimal newScore) {
        this.newScore = newScore;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }
}
