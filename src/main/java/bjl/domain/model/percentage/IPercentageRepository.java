package bjl.domain.model.percentage;


import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/8/14.
 */
public interface IPercentageRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
