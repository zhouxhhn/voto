package bjl.application.agent.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/2/27
 */
public class ListAgentCommand extends BasicPaginationCommand {

    private Integer level; // 0 全部  1 普通玩家 2 二级代理

    private String parentId;

    private String name; //昵称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
