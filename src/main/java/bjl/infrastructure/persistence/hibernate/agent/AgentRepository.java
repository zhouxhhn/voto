package bjl.infrastructure.persistence.hibernate.agent;

import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.IAgentRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Repository("agentRepository")
public class AgentRepository extends AbstractHibernateGenericRepository<Agent, String>
        implements IAgentRepository<Agent, String> {


    @Override
    public void delete(String parentId) {

        String sql = "DELETE from t_agent  where parent_id='"+parentId+"'";

        Query query = getSession().createSQLQuery(sql);
        query.executeUpdate();
    }
}
