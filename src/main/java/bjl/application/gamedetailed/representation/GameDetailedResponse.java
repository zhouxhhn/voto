package bjl.application.gamedetailed.representation;

import java.math.BigDecimal;
import java.util.Date;

/** 返回到前端的个人流水
 * Created by zhouxh on 2018/9/1.
 */
public class GameDetailedResponse {

    private Date createDate; //创建时间

    private Integer hallType;   //大厅类型

    private BigDecimal triratnaProfit; //三宝盈亏--交易金额

    private BigDecimal balance = new BigDecimal(0); //余额

    private String time; //时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalancee(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }

    public BigDecimal getTriratnaProfit() {
        return triratnaProfit;
    }

    public void setTriratnaProfit(BigDecimal triratnaProfit) {
        this.triratnaProfit = triratnaProfit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
