package bjl.infrastructure.persistence.hibernate.ratio;

import bjl.domain.model.ratio.IRatioRepository;
import bjl.domain.model.ratio.Ratio;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/3/22
 */
@Repository("ratioRepository")
public class RatioRepository extends AbstractHibernateGenericRepository<Ratio, String>
        implements IRatioRepository<Ratio, String>{
}
