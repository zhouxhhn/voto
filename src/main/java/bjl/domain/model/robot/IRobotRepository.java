package bjl.domain.model.robot;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/4/25
 */
public interface IRobotRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
