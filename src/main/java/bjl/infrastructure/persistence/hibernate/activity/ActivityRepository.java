package bjl.infrastructure.persistence.hibernate.activity;

import bjl.domain.model.activity.Activity;
import bjl.domain.model.activity.IActivityRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/6/21
 */
@Repository("activityRepository")
public class ActivityRepository extends AbstractHibernateGenericRepository<Activity,String>
        implements IActivityRepository<Activity,String> {
}
