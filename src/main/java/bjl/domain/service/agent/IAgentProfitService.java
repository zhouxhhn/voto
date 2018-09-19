package bjl.domain.service.agent;

import java.util.List;

import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.domain.model.agent.AgentProfit;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IAgentProfitService {

    void create(AgentProfit agentProfit);

    Pagination<AgentProfit> pagination(ListAgentProfitCommand command);

    Pagination<AgentProfit> exportList(ListAgentProfitCommand command);
}
