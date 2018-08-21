package bjl.application.phonebet.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/9.
 */
public class CreatePhoneBetCommand {

    private String username;
    private String phoneNo;
    private Integer hall;
    private BigDecimal score;


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getHall() {
        return hall;
    }

    public void setHall(Integer hall) {
        this.hall = hall;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
