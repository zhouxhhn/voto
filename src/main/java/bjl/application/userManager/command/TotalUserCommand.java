package bjl.application.userManager.command;

import java.math.BigDecimal;

/**
 * Created by weixing on 2018/1/14.
 */
public class TotalUserCommand {
    private BigDecimal  primeScore;//7.初始分
    private BigDecimal dateScore;//8.日积分
    private BigDecimal  totalScore;//9.总积分


    public BigDecimal getPrimeScore() {
        return primeScore;
    }

    public void setPrimeScore(BigDecimal primeScore) {
        this.primeScore = primeScore;
    }

    public BigDecimal getDateScore() {
        return dateScore;
    }

    public void setDateScore(BigDecimal dateScore) {
        this.dateScore = dateScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }
}
