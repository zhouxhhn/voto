package bjl.infrastructure.persistence.hibernate.financialSummary;

import bjl.application.financialSummary.command.TotalFinancialSummaryCommand;
import bjl.domain.model.financialSummary.FinancialSummary;
import bjl.domain.model.financialSummary.IFinancialSummaryRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dyp on 2018-1-10.
 */
@Repository("financialSummaryRepository")
public class FinancialSummaryRepository  extends AbstractHibernateGenericRepository<FinancialSummary,String>
        implements IFinancialSummaryRepository<FinancialSummary,String> {
    @Override
    public Object[] sum(List<Criterion> criterionList) {
        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Transformers.aliasToBean(TotalFinancialSummaryCommand.class));
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("playerScore"));
        projectionList.add(Projections.sum("bankerScore"));
        projectionList.add(Projections.sum("playerProportion"));
        projectionList.add(Projections.sum("bankerProportion"));
        projectionList.add(Projections.sum("playerMesaScore"));
        projectionList.add(Projections.sum("bankerMesaScore"));
        projectionList.add(Projections.sum("mesaWinLoss"));
        projectionList.add(Projections.sum("mesaWashCode"));
        projectionList.add(Projections.sum("zeroProfit"));
        projectionList.add(Projections.sum("hedgeProfit"));
        projectionList.add(Projections.sum("proportionProfit"));
        projectionList.add(Projections.sum("profitSummary"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();
        Object[] objects = (Object[]) list.get(0);
        return  objects;
    }

}
