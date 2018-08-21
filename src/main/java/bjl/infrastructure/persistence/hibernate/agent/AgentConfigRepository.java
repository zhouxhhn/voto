package bjl.infrastructure.persistence.hibernate.agent;

import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.agent.IAgentConfigRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Repository("agentConfigRepository")
public class AgentConfigRepository extends AbstractHibernateGenericRepository<AgentConfig,String>
        implements IAgentConfigRepository<AgentConfig, String> {


    @Override
    public void delete(String userId) {

        String sql = "DELETE from t_agent_config  where user_id='"+userId+"'";

        Query query = getSession().createSQLQuery(sql);
        query.executeUpdate();

    }
}
