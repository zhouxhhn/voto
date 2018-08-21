package bjl.domain.model.recharge;


import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/9/29.
 */
public interface IRechargeCtlRepository <T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
