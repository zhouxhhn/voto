package bjl.application.financialSummary.command;

import bjl.application.shared.command.SharedCommand;
import bjl.core.common.BasicPaginationCommand;

import java.math.BigDecimal;

/**
 * Created by dyp on 2017-12-20.
 */
//财务账面汇总
public class ListFinancialSummaryCommand  extends BasicPaginationCommand{
    private Integer boots; //鞋数
    private Integer games; //局数
    private String startDate;  //开始时间
    private String endDate;  //结束时间


    public Integer getBoots() {
        return boots;
    }

    public void setBoots(Integer boots) {
        this.boots = boots;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
