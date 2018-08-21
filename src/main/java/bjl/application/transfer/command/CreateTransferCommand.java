package bjl.application.transfer.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/12/26.
 */
public class CreateTransferCommand {

    private String transfer;        //转账用户
    private String receive;      //收款用户
    private BigDecimal score;       //转账分数
    private Integer type;   //类型 1，转入 2，转出

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
