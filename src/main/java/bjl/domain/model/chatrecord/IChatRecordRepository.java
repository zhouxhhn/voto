package bjl.domain.model.chatrecord;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by zhangjin on 2017/8/31.
 */
public interface IChatRecordRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    ChatRecord searchByDevice(String deviceNo);

    ChatRecord getMax(String deviceNo);
}
