package bjl.infrastructure.persistence.hibernate.bettable;

import bjl.domain.model.bettable.BetTable;
import bjl.domain.model.bettable.IBetTableRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/4/28
 */
@Repository("betTableRepository")
public class BetTableRepository extends AbstractHibernateGenericRepository<BetTable,String> implements IBetTableRepository<BetTable,String>{
}
