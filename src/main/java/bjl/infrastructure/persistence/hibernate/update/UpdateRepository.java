package bjl.infrastructure.persistence.hibernate.update;

import bjl.domain.model.update.IUpdateRepository;
import bjl.domain.model.update.Update;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/12/23.
 */
@Repository("updateRepository")
public class UpdateRepository extends AbstractHibernateGenericRepository<Update, String>
        implements IUpdateRepository<Update, String>{
}
