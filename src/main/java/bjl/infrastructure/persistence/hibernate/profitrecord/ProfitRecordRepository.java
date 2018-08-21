package bjl.infrastructure.persistence.hibernate.profitrecord;

import bjl.domain.model.profitrecord.IProfitRecordRepository;
import bjl.domain.model.profitrecord.ProfitRecord;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/1/30
 */
@Repository("profitRecordRepository")
public class ProfitRecordRepository extends AbstractHibernateGenericRepository<ProfitRecord, String>
        implements IProfitRecordRepository<ProfitRecord, String> {
}
