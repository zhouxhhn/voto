package bjl.domain.model.ratiocheck;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/3/1
 */
public class RatioCheck extends ConcurrencySafeEntity {

    private BigDecimal oldR;   //原R值
    private BigDecimal oldHigh;   //原最高占比
    private BigDecimal oldFirstRatio;   //原一级占比
    private BigDecimal oldSecondRatio;   //原二级占比

    private BigDecimal newR;   //现R值
    private BigDecimal newHigh;   //现最高占比
    private BigDecimal newFirstRatio;   //现一级占比
    private BigDecimal newSecondRatio;   //现二级占比

    private Account parent; //上级
    private Account user;   //下级
    private Account play; //普通玩家
    private Integer status; //状态   1.待审核 2.审核通过 3.审核未通过 4.配置不存在 5.配置超范围 6.配置错误
    private Integer type; //类型  1.一级改二级 2.一级改玩家 3.二级改玩家

    public BigDecimal getOldR() {
        return oldR;
    }

    public void setOldR(BigDecimal oldR) {
        this.oldR = oldR;
    }

    public BigDecimal getOldHigh() {
        return oldHigh;
    }

    public void setOldHigh(BigDecimal oldHigh) {
        this.oldHigh = oldHigh;
    }

    public BigDecimal getOldFirstRatio() {
        return oldFirstRatio;
    }

    public void setOldFirstRatio(BigDecimal oldFirstRatio) {
        this.oldFirstRatio = oldFirstRatio;
    }

    public BigDecimal getOldSecondRatio() {
        return oldSecondRatio;
    }

    public void setOldSecondRatio(BigDecimal oldSecondRatio) {
        this.oldSecondRatio = oldSecondRatio;
    }

    public BigDecimal getNewR() {
        return newR;
    }

    public void setNewR(BigDecimal newR) {
        this.newR = newR;
    }

    public BigDecimal getNewHigh() {
        return newHigh;
    }

    public void setNewHigh(BigDecimal newHigh) {
        this.newHigh = newHigh;
    }

    public BigDecimal getNewFirstRatio() {
        return newFirstRatio;
    }

    public void setNewFirstRatio(BigDecimal newFirstRatio) {
        this.newFirstRatio = newFirstRatio;
    }

    public BigDecimal getNewSecondRatio() {
        return newSecondRatio;
    }

    public void setNewSecondRatio(BigDecimal newSecondRatio) {
        this.newSecondRatio = newSecondRatio;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Account getPlay() {
        return play;
    }

    public void setPlay(Account play) {
        this.play = play;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
