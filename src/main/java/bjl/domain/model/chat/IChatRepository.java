package bjl.domain.model.chat;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface IChatRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
