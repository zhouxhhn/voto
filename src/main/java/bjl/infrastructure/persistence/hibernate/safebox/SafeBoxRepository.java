package bjl.infrastructure.persistence.hibernate.safebox;

import bjl.domain.model.safebox.ISafeBoxRepository;
import bjl.domain.model.safebox.SafeBox;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/9/15.
 */
@Repository("safeBoxRepository")
public class SafeBoxRepository extends AbstractHibernateGenericRepository<SafeBox,String>
        implements ISafeBoxRepository<SafeBox,String> {
}
