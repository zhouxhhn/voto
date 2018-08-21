package bjl.domain.model.profitrecord;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2018/1/30
 */
public interface IProfitRecordRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
