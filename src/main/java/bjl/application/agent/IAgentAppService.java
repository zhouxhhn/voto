package bjl.application.agent;

import bjl.application.agent.command.ListAgentCommand;
import bjl.application.agent.reprensentation.AgentRepresentation;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IAgentAppService {

    Agent create(Account account, Account parent);

    Account getAgent(Account user);

    void delete(Account account);

    List<Agent> agentList(Account parent);

    Pagination<AgentRepresentation> getLower(ListAgentCommand command);

    Integer count(Account account);

    String edit(String id, BigDecimal high, BigDecimal fact, BigDecimal R);

    String edit(String id,BigDecimal nFact, BigDecimal R);

    String editSecond(String id,BigDecimal nFact, BigDecimal R);

    String editFirst(String id,BigDecimal firstRatio,BigDecimal secondRatio, BigDecimal R);

    List<Agent> listAll();
}
