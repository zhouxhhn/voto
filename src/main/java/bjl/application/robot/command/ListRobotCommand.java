package bjl.application.robot.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/4/26
 */
public class ListRobotCommand extends BasicPaginationCommand{

    private String name;      //机器人昵称
    private Integer hallType; //机器人所属大厅

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHallType() {
        return hallType;
    }

    public void setHallType(Integer hallType) {
        this.hallType = hallType;
    }
}
