package bjl.application.agent.command;

import bjl.core.common.BasicPaginationCommand;

/**
 * Created by zhangjin on 2018/1/15.
 */
public class ListAgentConfigCommand extends BasicPaginationCommand {

    private String agentName;
    private Integer level;
    private String parentId;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
