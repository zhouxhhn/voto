package bjl.domain.model.update;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/12/23.
 */
public interface IUpdateRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
