package bjl.application.playerlose.command;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class TotalPlayerLoseCommand {

    private BigDecimal bankPlayProfit;
    private BigDecimal triratnaProfit;
    private BigDecimal effective;
    private BigDecimal bankPlayLose;
    private BigDecimal triratnaLose;

    public BigDecimal getBankPlayProfit() {
        return bankPlayProfit;
    }

    public void setBankPlayProfit(BigDecimal bankPlayProfit) {
        this.bankPlayProfit = bankPlayProfit;
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

    public BigDecimal getBankPlayLose() {
        return bankPlayLose;
    }

    public void setBankPlayLose(BigDecimal bankPlayLose) {
        this.bankPlayLose = bankPlayLose;
    }

    public BigDecimal getTriratnaLose() {
        return triratnaLose;
    }

    public void setTriratnaLose(BigDecimal triratnaLose) {
        this.triratnaLose = triratnaLose;
    }

    public TotalPlayerLoseCommand(List list) {

        Object[] objects = (Object[]) list.get(0);
        if(objects != null){

            this.bankPlayProfit = (BigDecimal)objects[0];
            this.triratnaProfit = (BigDecimal)objects[1];
            this.effective = (BigDecimal)objects[2];
            this.bankPlayLose = (BigDecimal)objects[3];
            this.triratnaLose = (BigDecimal)objects[4];
        }else {
            BigDecimal bigDecimal = BigDecimal.valueOf(0);
            this.bankPlayProfit = bigDecimal;
            this.triratnaProfit = bigDecimal;
            this.effective = bigDecimal;
            this.bankPlayLose = bigDecimal;
            this.triratnaLose = bigDecimal;
        }
    }
}
