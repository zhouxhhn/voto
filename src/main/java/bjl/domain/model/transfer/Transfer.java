package bjl.domain.model.transfer;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/9/14.
 */
public class Transfer extends ConcurrencySafeEntity {

    private Account transfer;        //转账用户
    private Account receive;      //收款用户
    private BigDecimal score;       //转账分数
    private BigDecimal transferScore; //转账用户现有积分
    private BigDecimal receiveScore; //收款用户现有积分
    private Integer status; //转账状态   1 待审核 2允许 3通过

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getReceiveScore() {
        return receiveScore;
    }

    public void setReceiveScore(BigDecimal receiveScore) {
        this.receiveScore = receiveScore;
    }

    public BigDecimal getTransferScore() {
        return transferScore;
    }

    public void setTransferScore(BigDecimal transferScore) {
        this.transferScore = transferScore;
    }

    public Account getTransfer() {
        return transfer;
    }

    public void setTransfer(Account transfer) {
        this.transfer = transfer;
    }

    public Account getReceive() {
        return receive;
    }

    public void setReceive(Account receive) {
        this.receive = receive;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Transfer() {

    }

    public Transfer(BigDecimal score) {
        this.score = score;
    }
}
