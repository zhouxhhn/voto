package bjl.infrastructure.persistence.hibernate.withdrawa;

import bjl.domain.model.withdraw.IWithdrawRepository;
import bjl.domain.model.withdraw.Withdraw;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/1/23
 */
@Repository("withdrawRepository")
public class WithdrawRepository extends AbstractHibernateGenericRepository<Withdraw,String>
        implements IWithdrawRepository<Withdraw,String> {
}
