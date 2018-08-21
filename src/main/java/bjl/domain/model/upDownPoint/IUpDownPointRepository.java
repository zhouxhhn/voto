package bjl.domain.model.upDownPoint;

import bjl.application.upDownPoint.command.CreateUpDownPoint;
import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dyp on 2017-12-22.
 */
public interface IUpDownPointRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
    void create(ModifyUserCommand createUpDownPoint);

    Map<String, BigDecimal> sum(Date date);
}
