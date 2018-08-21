package bjl.domain.model.ip;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/11/1.
 */
public interface IIpRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    Ip searchByIp(String ip);
}
