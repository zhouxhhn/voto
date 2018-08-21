package bjl.application.playerlose.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class ListPlayerLoseCommand extends BasicPaginationCommand {

    private Integer boots;
    private Integer games;
    private String name;
    private String startDate;
    private String endDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
