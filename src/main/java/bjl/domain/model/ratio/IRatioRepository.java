package bjl.domain.model.ratio;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/3/22
 */
public interface IRatioRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
