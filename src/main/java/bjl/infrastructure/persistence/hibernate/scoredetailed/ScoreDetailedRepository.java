package bjl.infrastructure.persistence.hibernate.scoredetailed;

import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Repository("scoreDetailedRepository")
public class ScoreDetailedRepository extends AbstractHibernateGenericRepository<ScoreDetailed, String>
        implements IScoreDetailedRepository<ScoreDetailed, String>{
}
