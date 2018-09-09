package bjl.infrastructure.persistence.hibernate.publicRecharge;

import bjl.domain.model.activity.Activity;
import bjl.domain.model.activity.IActivityRepository;
import bjl.domain.model.publicRecharge.IPublicRechargeRepository;
import bjl.domain.model.publicRecharge.PublicRecharge;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/6/21
 */
@Repository("publicRechargeRepository")
public class PublicRechargeRepository extends AbstractHibernateGenericRepository<PublicRecharge,String>
        implements IPublicRechargeRepository<PublicRecharge,String> {
}
