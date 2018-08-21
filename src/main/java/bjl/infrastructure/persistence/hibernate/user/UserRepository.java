package bjl.infrastructure.persistence.hibernate.user;


import bjl.application.userManager.command.TotalUserCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.cglib.core.Transformer;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pengyi on 2016/4/15.
 */
@Repository("userRepository")
public class UserRepository extends AbstractHibernateGenericRepository<User, String>
        implements IUserRepository<User, String> {

    @Override
    public Integer getMaxSerial() {
        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        criteriaCount.setProjection(Projections.max("serial"));
        return (Integer) criteriaCount.uniqueResult();
    }

    @Override
    public User searchByAccount(Account account) {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.eq("account", account));

        return (User)criteria.uniqueResult();
    }
//计算出数据库user表中日积分、总积分，剩余分、初始分的总和
    public Object[] sum(List<Criterion> criterionList){
        Criteria criteria=getSession().createCriteria(getPersistentClass());
        criteria.setResultTransformer(Transformers.aliasToBean(TotalUserCommand.class));
        if (null!=criterionList) {
            criterionList.forEach(criteria::add);
        }
        criteria.add(Restrictions.eq("virtual",2));
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("primeScore"));
        projectionList.add(Projections.sum("score"));
        projectionList.add(Projections.sum("bankScore"));
        projectionList.add(Projections.sum("dateScore"));

        criteria.setProjection(projectionList);
        List list = criteria.list();
        Object[] objects = (Object[]) list.get(0);
        return  objects;
    }
}
