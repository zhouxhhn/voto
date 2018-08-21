package bjl.domain.model.systemconfig;


import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by pengyi on 2016/5/6.
 */
public interface ISystemConfigRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
