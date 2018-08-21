package bjl.domain.service.agent;

import bjl.application.agent.command.ListAgentCommand;
import bjl.core.timer.AgentProfitTimer;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IAgentService {

    Agent create(Account user, Account parent);

    Account getAgent(Account user);

    void delete(Account account);

    List<Agent> agentList(Account parent);

    Integer count(Account account);

    List<Agent> listAll();

    Pagination<Agent> getLower(ListAgentCommand command);

    String edit(String id, BigDecimal high, BigDecimal fact, BigDecimal R);

    String edit(String id,BigDecimal nFact, BigDecimal R);

    String editSecond(String id,BigDecimal nFact, BigDecimal R);

    String editFirst(String id,BigDecimal firstRatio,BigDecimal secondRatio, BigDecimal R);
}
