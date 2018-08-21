package bjl.domain.model.scoretable;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/4/28
 */
public interface IScoreTableRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
