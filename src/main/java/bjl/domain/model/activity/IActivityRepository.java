package bjl.domain.model.activity;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface IActivityRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
