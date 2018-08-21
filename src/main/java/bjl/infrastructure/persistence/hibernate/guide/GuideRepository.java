package bjl.infrastructure.persistence.hibernate.guide;

import bjl.domain.model.guide.Guide;
import bjl.domain.model.guide.IGuideRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/6/21
 */
@Repository("guideRepository")
public class GuideRepository extends AbstractHibernateGenericRepository<Guide,String>
        implements IGuideRepository<Guide,String> {
}
