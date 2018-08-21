package bjl.infrastructure.persistence.hibernate.percentage;

import bjl.domain.model.percentage.IPercentageRepository;
import bjl.domain.model.percentage.Percentage;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/8/14.
 */
@Repository("percentageRepository")
public class PercentageRepository extends AbstractHibernateGenericRepository<Percentage, String>
        implements IPercentageRepository<Percentage, String> {
}
