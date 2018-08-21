package bjl.infrastructure.persistence.hibernate.UpDownPoint;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.upDownPoint.command.CreateUpDownPoint;
import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.domain.model.upDownPoint.IUpDownPointRepository;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.apache.shiro.SecurityUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dyp on 2017-12-22.
 */
@Repository("upDownPointRepository")
public class UpDownPointRepository extends AbstractHibernateGenericRepository<UpDownPoint,String>
        implements IUpDownPointRepository<UpDownPoint,String> {

    @Autowired
    private IUserRepository<User,String> userRepository;
    @Autowired
    private IUpDownPointRepository<UpDownPoint,String> upDownPointRepository;
    @Override
    public void create(ModifyUserCommand createUpDownPoint) {
        if(createUpDownPoint.getUpDownPoint()!=null){
            User user1 = userRepository.getById(createUpDownPoint.getId());
            String userName=user1.getPlayerAlias();
            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            UpDownPoint upDownPoint=new UpDownPoint();
            upDownPoint.setCreateDate(new Date());
            upDownPoint.setOperationUser(sessionUser.getUserName());
            upDownPoint.setUserName(user1.getAccount().getUserName());
            upDownPoint.setUpDownPoint(createUpDownPoint.getUpDownPoint());
            if(createUpDownPoint.getUpDownPoint().compareTo(BigDecimal.valueOf(0))>=0){
                upDownPoint.setUpDownPointType(1);//上分
            }else{
                upDownPoint.setUpDownPointType(2);//下分
            }
            upDownPointRepository.save(upDownPoint);
        }
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


//        criteriaCount.add(Restrictions.eq("userName",username));
        if(date != null){
            criteriaCount.add(Restrictions.ge("createDate",date));
        }
        //只统计上分，下分不统计
        criteriaCount.add(Restrictions.gt("upDownPoint",BigDecimal.valueOf(0)));
        //聚合  统计每个玩家的洗码量
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("upDownPoint"));
        //分组
        projectionList.add(Projections.groupProperty("userName"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        Map<String, BigDecimal> map = new HashMap<>();

        for(Object object : list){
            Object[] objects = (Object[]) object;
            map.put((String) objects[1],(BigDecimal) objects[0]);
        }

        return map;

    }
}
