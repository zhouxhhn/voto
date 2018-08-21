package bjl.application.upDownPoint.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/2/9
 */
public class SumUpDownPoint {

    private String userName;

    private BigDecimal addScore;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAddScore() {
        return addScore;
    }

    public void setAddScore(BigDecimal addScore) {
        this.addScore = addScore;
    }

    public SumUpDownPoint(Object[] objects) {
        this.userName = (String) objects[1];
        this.addScore = (BigDecimal) objects[0];
    }
}
