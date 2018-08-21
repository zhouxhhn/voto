package bjl.infrastructure.persistence.hibernate.systemconfig;

import bjl.domain.model.systemconfig.ISystemConfigRepository;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/4/15.
 */
@Repository("systemConfigRepository")
public class SystemConfigRepository extends AbstractHibernateGenericRepository<SystemConfig, String>
        implements ISystemConfigRepository<SystemConfig, String> {
}
