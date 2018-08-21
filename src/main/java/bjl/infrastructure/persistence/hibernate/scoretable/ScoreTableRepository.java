package bjl.infrastructure.persistence.hibernate.scoretable;

import bjl.domain.model.scoretable.IScoreTableRepository;
import bjl.domain.model.scoretable.ScoreTable;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/4/28
 */
@Repository("scoreTableRepository")
public class ScoreTableRepository extends AbstractHibernateGenericRepository<ScoreTable,String> implements IScoreTableRepository<ScoreTable,String> {
}
