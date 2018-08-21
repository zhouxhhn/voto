package bjl.infrastructure.persistence.hibernate.logger;

import bjl.domain.model.logger.ILoggerRepository;
import bjl.domain.model.logger.Logger;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Author pengyi
 * Date 17-4-21.
 */
@Repository("loggerRepository")
public class LoggerRepository extends AbstractHibernateGenericRepository<Logger, String>
        implements ILoggerRepository<Logger, String> {
}
