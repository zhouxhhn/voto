package bjl.domain.model.withdraw;


import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/9/5.
 */
public interface IWithdrawRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

}
