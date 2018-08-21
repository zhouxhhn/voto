package bjl.domain.model.agent;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/1/12.
 */
public interface IAgentProfitRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
