package bjl.domain.model.agent;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IAgentRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    void delete(String parentId);

}
