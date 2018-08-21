package bjl.domain.model.financialSummary;

import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dyp on 2018-1-10.
 */
public interface IFinancialSummaryRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
   Object[] sum(List<Criterion> criterionList);
}
