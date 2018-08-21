package bjl.infrastructure.persistence.hibernate.recharge;

import bjl.core.enums.YesOrNoStatus;
import bjl.domain.model.account.Account;
import bjl.domain.model.recharge.IRechargeRepository;
import bjl.domain.model.recharge.Recharge;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.serial.SerialStruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by pengyi on 16-7-9.
 */
@Repository("rechargeRepository")
public class RechargeRepository extends AbstractHibernateGenericRepository<Recharge, String>
        implements IRechargeRepository<Recharge, String> {

    @Override
    public BigDecimal total(List<Criterion> criterionList, Map<String, String> aliasMap) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }

        if (null != aliasMap) {
            for (Map.Entry<String, String> entry : aliasMap.entrySet()) {
                criteriaCount.createAlias(entry.getKey(), entry.getValue());
            }
        }
        criteriaCount.setProjection(Projections.sum("money"));

        return (BigDecimal) criteriaCount.uniqueResult();
    }

    @Override
    public Recharge byOrderNo(String orderNo) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("orderNo", orderNo));
        return (Recharge) criteria.uniqueResult();
    }

    @Override
    public Map<String, BigDecimal> sum(Date date) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Transformers.aliasToBean(BigDecimal.class));
        //时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date date = calendar.getTime(); //当天零时零分了零秒的时间
//
//        calendar.add(Calendar.DATE, -1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
//        Date before = calendar.getTime(); //上一天零时零分了零秒的时间

        if (date != null) {
            criteriaCount.add(Restrictions.ge("createDate", date));
        }
        criteriaCount.add(Restrictions.eq("isSuccess", YesOrNoStatus.YES));
        //聚合  统计每个玩家的充值数
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("money"));
        //分组
        projectionList.add(Projections.groupProperty("account"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        Map<String,BigDecimal> map = new HashMap<>();
        for(Object object : list){
            Object[] objects = (Object[]) object;
            map.put(((Account)objects[1]).getUserName(),(BigDecimal)objects[0]);
        }
        return map;
    }

    @Override
    public List<Object[]> list(String username) {

        String sql = "SELECT r.account_id,r.create_date,money,r.is_success,'1' as type from t_recharge as r,t_account as a where r.account_id = a.id and a.user_name='"+username+"' \n" +
                "UNION ALL \n" +
                "SELECT r.account_id,r.create_date,money,r.`status`,'2' as type from t_withdraw as r,t_account as a where r.account_id = a.id and a.user_name='"+username+"' ";

        Query query = getSession().createSQLQuery(sql);
        List list = query.list();

        List<Object[]> list1 = new ArrayList<>();
        for(Object object : list){
            Object[] objects = (Object[]) object;
            list1.add(objects);
        }
        return list1;
    }

}
