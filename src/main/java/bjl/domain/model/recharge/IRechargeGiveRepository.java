package bjl.domain.model.recharge;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by pengyi on 16-7-9.
 */
public interface IRechargeGiveRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

}
