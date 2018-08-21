package bjl.domain.model.safebox;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/9/15.
 */
public interface ISafeBoxRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
