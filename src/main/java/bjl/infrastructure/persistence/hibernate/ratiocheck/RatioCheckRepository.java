package bjl.infrastructure.persistence.hibernate.ratiocheck;

import bjl.domain.model.ratiocheck.IRatioCheckRepository;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/3/1
 */
@Repository("ratioCheckRepository")
public class RatioCheckRepository extends AbstractHibernateGenericRepository<RatioCheck, String>
        implements IRatioCheckRepository<RatioCheck, String>{

}
