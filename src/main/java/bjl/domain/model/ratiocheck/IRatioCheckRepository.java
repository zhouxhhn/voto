package bjl.domain.model.ratiocheck;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/3/1
 */
public interface IRatioCheckRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
