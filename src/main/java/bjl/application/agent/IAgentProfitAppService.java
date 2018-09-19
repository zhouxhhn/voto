package bjl.application.agent;

import java.util.List;

import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.domain.model.agent.AgentProfit;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IAgentProfitAppService {

    void create(AgentProfit agentProfit);

    Pagination<AgentProfit> pagination(ListAgentProfitCommand command,String flag);

    Pagination<AgentProfit>  exportList(ListAgentProfitCommand command,String flag);
}
