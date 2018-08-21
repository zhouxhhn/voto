package bjl.infrastructure.persistence.hibernate.rechargectl;

import bjl.domain.model.recharge.IRechargeCtlRepository;
import bjl.domain.model.recharge.RechargeCtl;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/9/29.
 */
@Component
public class RechargeCtlRepository extends AbstractHibernateGenericRepository<RechargeCtl,String>
        implements IRechargeCtlRepository<RechargeCtl,String>{
}
