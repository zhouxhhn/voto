package bjl.domain.model.transfer;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/9/14.
 */
public interface ITransferRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
