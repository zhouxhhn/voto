package bjl.application.phonebet.representation;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2018/4/9
 */
public class PhoneBetRepresentation {

    private String id;
    private String user;  //用户
    private String phoneNo;  //电话号码
    private BigDecimal firstRatio; //一级占比
    private BigDecimal secondRatio; //二级占比
    private Date startDate; //开始时间
    private Date endDate;   //结束时间
    private Integer hall;  //大厅类型
    private BigDecimal frozenScore; //冻结分数
    private BigDecimal restScore; //剩余分数
    private BigDecimal loseScore; //洗码
    private Integer status; //状态  1:等待接待中 2：接待中 2：已完成
    private String jobNum;//工号
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getHall() {
        return hall;
    }

    public void setHall(Integer hall) {
        this.hall = hall;
    }

    public BigDecimal getFrozenScore() {
        return frozenScore;
    }

    public void setFrozenScore(BigDecimal frozenScore) {
        this.frozenScore = frozenScore;
    }

    public BigDecimal getRestScore() {
        return restScore;
    }

    public void setRestScore(BigDecimal restScore) {
        this.restScore = restScore;
    }

    public BigDecimal getLoseScore() {
        return loseScore;
    }

    public void setLoseScore(BigDecimal loseScore) {
        this.loseScore = loseScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
}
