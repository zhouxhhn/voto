package bjl.domain.model.spreadprofit;

import bjl.application.spreadprofit.command.ProfitDetailedCommand;
import bjl.application.spreadprofit.command.SpreadProfitCommand;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/4/16
 */
public interface ISpreadProfitRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    int total(String userId);

    int effective(String userId);

    List<SpreadProfitCommand> vote(Date date);

    Object[] todaySUm(String dateStr, String username);

    Object[] todayProfit(String id);

    Object[] yesterdayProfit(String id);

    Object[] weekProfit(String id);

    Object[] lastWeekProfit(String id);

    Object[] monthProfit(String id);

    List<ProfitDetailedCommand> profitDetailed(String id);
}
