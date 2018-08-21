package bjl.domain.model.robot;

import bjl.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;

/**
 * 机器人实体类
 * Created by zhangjin on 2018/4/25
 */
public class Robot extends ConcurrencySafeEntity {

    private String name;        //昵称
    private String head;        //头像
    private BigDecimal score; //剩余分
    private BigDecimal primeScore;// 初始分
    private Integer hallType; //所属大厅
    private Integer bankPlayMin;//庄闲最小下注金额
    private Integer bankPlayMax;//庄闲最大下注金额
    private BigDecimal bankPlayRatio; //庄闲下注几率
    private Integer triratnaMin;//三宝最小下注金额
    private Integer triratnaMax;//三宝最大下注金额
    private BigDecimal triratnaRatio; //三宝下注几率
    private Integer frequency;  //下注次数
    private BigDecimal scoreMin; //下限分数，少于此分数则自动加分
    private BigDecimal addScore; //少于下限时，加分分数


    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getPrimeScore() {
        return primeScore;
    }

    public void setPrimeScore(BigDecimal primeScore) {
        this.primeScore = primeScore;
    }

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }

    public Integer getBankPlayMin() {
        return bankPlayMin;
    }

    public void setBankPlayMin(Integer bankPlayMin) {
        this.bankPlayMin = bankPlayMin;
    }

    public Integer getBankPlayMax() {
        return bankPlayMax;
    }

    public void setBankPlayMax(Integer bankPlayMax) {
        this.bankPlayMax = bankPlayMax;
    }

    public BigDecimal getBankPlayRatio() {
        return bankPlayRatio;
    }

    public void setBankPlayRatio(BigDecimal bankPlayRatio) {
        this.bankPlayRatio = bankPlayRatio;
    }

    public Integer getTriratnaMin() {
        return triratnaMin;
    }

    public void setTriratnaMin(Integer triratnaMin) {
        this.triratnaMin = triratnaMin;
    }

    public Integer getTriratnaMax() {
        return triratnaMax;
    }

    public void setTriratnaMax(Integer triratnaMax) {
        this.triratnaMax = triratnaMax;
    }

    public BigDecimal getTriratnaRatio() {
        return triratnaRatio;
    }

    public void setTriratnaRatio(BigDecimal triratnaRatio) {
        this.triratnaRatio = triratnaRatio;
    }

    public BigDecimal getScoreMin() {
        return scoreMin;
    }

    public void setScoreMin(BigDecimal scoreMin) {
        this.scoreMin = scoreMin;
    }

    public BigDecimal getAddScore() {
        return addScore;
    }

    public void setAddScore(BigDecimal addScore) {
        this.addScore = addScore;
    }
}
