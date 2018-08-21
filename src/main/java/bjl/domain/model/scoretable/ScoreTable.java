package bjl.domain.model.scoretable;

import bjl.core.id.ConcurrencySafeEntity;

import java.util.List;

/**
 * Created by zhangjin on 2018/4/28
 */
public class ScoreTable extends ConcurrencySafeEntity {

    private Integer hallType; //大厅类型
    private Integer boots; //靴
    private Integer games; //局
    private List<Object[]> betInfo;

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }

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

    public List<Object[]> getBetInfo() {
        return betInfo;
    }

    public void setBetInfo(List<Object[]> betInfo) {
        this.betInfo = betInfo;
    }
}
