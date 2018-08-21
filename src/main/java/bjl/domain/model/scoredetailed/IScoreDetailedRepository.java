package bjl.domain.model.scoredetailed;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IScoreDetailedRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
