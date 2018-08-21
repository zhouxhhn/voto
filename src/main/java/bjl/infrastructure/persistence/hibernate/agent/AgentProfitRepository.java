package bjl.infrastructure.persistence.hibernate.agent;

import bjl.domain.model.agent.AgentProfit;
import bjl.domain.model.agent.IAgentProfitRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/1/12.
 */
@Repository("agentProfitRepository")
public class AgentProfitRepository extends AbstractHibernateGenericRepository<AgentProfit, String>
        implements IAgentProfitRepository<AgentProfit, String> {
}
