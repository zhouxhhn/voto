package bjl.application.agent.command;

import bjl.domain.model.user.User;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/12.
 */
public class CountGameDetailedCommand {

    private BigDecimal playerLose; //玩家洗码
    private Integer type; //类型  1微投 2电投
    private User user;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPlayerLose() {
        return playerLose;
    }

    public void setPlayerLose(BigDecimal playerLose) {
        this.playerLose = playerLose;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public CountGameDetailedCommand(Object[] objects,int type) {

        if(type ==1 ){
            this.playerLose = ((BigDecimal) objects[0]).add((BigDecimal) objects[1]);
            this.user = (User) objects[2];
        } else if(type == 2){
            this.playerLose = (BigDecimal) objects[0];
            this.user = (User) objects[1];
        }
        this.type = type;
    }
}
