package bjl.infrastructure.persistence.hibernate.phonebet;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.domain.model.phonebet.IPhoneBetRepository;
import bjl.domain.model.phonebet.PhoneBet;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/9.
 */
@Repository("phoneBetRepository")
public class PhoneBetRepository extends AbstractHibernateGenericRepository<PhoneBet, String>
        implements IPhoneBetRepository<PhoneBet, String> {


    @Override
    public Object[] total(List<Criterion> criterionList) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("restScore"));
        projectionList.add(Projections.sum("loseScore"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();
        if (list.size() == 0){
            return new Object[]{0,0};
        }else {
            return (Object[]) list.get(0);
        }
    }

    @Override
    public List<CountGameDetailedCommand> count(Date date) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Transformers.aliasToBean(CountGameDetailedCommand.class));
        //时间
        if(date != null){
            criteriaCount.add(Restrictions.ge("createDate",date));

        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date date = calendar.getTime(); //当天零时零分了零秒的时间
//
//        calendar.add(Calendar.DATE, -1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
//        Date before = calendar.getTime(); //上一天零时零分了零秒的时间

        //聚合  统计每个玩家的洗码量
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("loseScore"));
        //分组
        projectionList.add(Projections.groupProperty("user"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();
        List<CountGameDetailedCommand> commands = new ArrayList<>();
        for(Object object : list){
            commands.add(new CountGameDetailedCommand((Object[]) object,2));
        }
        return commands;
    }
}
