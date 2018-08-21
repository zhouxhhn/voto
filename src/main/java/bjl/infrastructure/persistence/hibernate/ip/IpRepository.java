package bjl.infrastructure.persistence.hibernate.ip;

import bjl.domain.model.ip.IIpRepository;
import bjl.domain.model.ip.Ip;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/11/1.
 */
@Repository("ipRepository")
public class IpRepository extends AbstractHibernateGenericRepository<Ip,String>
        implements IIpRepository<Ip,String> {


    @Override
    public Ip searchByIp(String ip) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.like("ip", ip, MatchMode.ANYWHERE));
        return (Ip) criteria.uniqueResult();
    }
}
