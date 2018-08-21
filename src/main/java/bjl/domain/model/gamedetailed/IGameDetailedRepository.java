package bjl.domain.model.gamedetailed;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.playerlose.command.TotalPlayerLoseCommand;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/3.
 */
public interface IGameDetailedRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    TotalGameDetailedCommand total(List<Criterion> criterionList,String parentId);

    List<CountGameDetailedCommand> count(Date date);

    TotalPlayerLoseCommand count(List<Criterion> criterionList,String name);

    TotalTriratna totalTriratna(List<Criterion> criterionList);

    String[] ids(String parentId);

    Object[] personalSum(List<Criterion> criterionList);

}
