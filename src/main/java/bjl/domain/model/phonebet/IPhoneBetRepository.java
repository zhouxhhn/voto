package bjl.domain.model.phonebet;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/9.
 */
public interface IPhoneBetRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    Object[] total(List<Criterion> criterionList);

    List<CountGameDetailedCommand> count(Date date);
}
