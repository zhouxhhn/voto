package bjl.infrastructure.persistence.hibernate.chatrecord;

import bjl.domain.model.chatrecord.ChatRecord;
import bjl.domain.model.chatrecord.IChatRecordRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/8/31.
 */
@Repository
public class ChatRecordRepository extends AbstractHibernateGenericRepository<ChatRecord, String>
        implements IChatRecordRepository<ChatRecord, String> {

    /**
     * 按设备号查找
     * @param deviceNo
     * @return
     */
    @Override
    public ChatRecord searchByDevice(String deviceNo) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("deviceno", deviceNo));
        return (ChatRecord) criteria.uniqueResult();
    }

    /**
     * 获取序号最大记录
     * @param deviceNo
     * @return
     */
    @Override
    public ChatRecord getMax(String deviceNo) {
        String sql = "select * from t_chatrecord as a where a.serial=(select max(serial) as serial FROM t_chatrecord as c where c.device_no='"+deviceNo+"') and a.device_no='"+deviceNo+"'";
        SQLQuery query = getSession().createSQLQuery(sql).addEntity(ChatRecord.class);
        ChatRecord chatRecord =  (ChatRecord) query.uniqueResult();
        return chatRecord;
    }
}
