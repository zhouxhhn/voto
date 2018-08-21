package bjl.domain.model.bettable;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/4/28
 */
public interface IBetTableRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

}
