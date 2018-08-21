package bjl.domain.service.agent;

import bjl.application.agent.command.EditAgentConfigCommand;
import bjl.application.agent.command.ListAgentConfigCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.role.Role;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IAgentConfigService {

    Pagination<AgentConfig> pagination(ListAgentConfigCommand command);

    Pagination<Agent> paginationS(ListAgentConfigCommand command);


    void create(Account account, Role role);

    AgentConfig edit(EditAgentConfigCommand command);

    void delete(Account account);

    AgentConfig getByAccount(Account account);

    void update(AgentConfig agentConfig);

    AgentConfig getByAccountId(String id);

    int update(String id, BigDecimal factRatio);
}
