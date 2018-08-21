package bjl.infrastructure.persistence.hibernate.robot;

import bjl.domain.model.robot.IRobotRepository;
import bjl.domain.model.robot.Robot;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2018/4/25
 */
@Repository("robotRepository")
public class RobotRepository extends AbstractHibernateGenericRepository<Robot,String> implements IRobotRepository<Robot,String>{

}
