package bjl.infrastructure.persistence.hibernate.gamedetailed;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.playerlose.command.TotalPlayerLoseCommand;
import bjl.application.playerlose.reprensentation.PlayerLoseRepresentation;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 *
 * Created by zhangjin on 2018/1/3.
 */
@Repository("gmeDetailedRepository")
public class GameDetailedRepository extends AbstractHibernateGenericRepository<GameDetailed,String>
        implements IGameDetailedRepository<GameDetailed,String> {


    @Override
    public TotalGameDetailedCommand total(List<Criterion> criterionList, String parentId) {
        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Criteria.ROOT_ENTITY);
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }
        criteriaCount.createAlias("user","user");
        if(!CoreStringUtils.isEmpty(parentId)){
            criteriaCount.createAlias("user.account","account");
        }
        criteriaCount.createAlias("user.account","account");
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("player"));
        projectionList.add(Projections.sum("banker"));
        projectionList.add(Projections.sum("playerPair"));
        projectionList.add(Projections.sum("bankPair"));
        projectionList.add(Projections.sum("draw"));
        projectionList.add(Projections.sum("bankPlayProfit"));
        projectionList.add(Projections.sum("triratnaProfit"));
        projectionList.add(Projections.sum("effective"));
        projectionList.add(Projections.sum("bankPlayLose"));
        projectionList.add(Projections.sum("triratnaLose"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        return new TotalGameDetailedCommand(list);
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
        projectionList.add(Projections.sum("bankPlayLose"));
        projectionList.add(Projections.sum("triratnaLose"));
        //分组
        projectionList.add(Projections.groupProperty("user"));
        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        List<CountGameDetailedCommand> commands = new ArrayList<>();
        for(Object object : list){
            commands.add(new CountGameDetailedCommand((Object[]) object,1));
        }

        return commands;
    }

    @Override
    public TotalPlayerLoseCommand count(List<Criterion> criterionList,String name) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Transformers.aliasToBean(PlayerLoseRepresentation.class));

        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }
        if(!CoreStringUtils.isEmpty(name)){
            criteriaCount.createAlias("user.account","account");
        }
        //不统计虚拟玩家
        criteriaCount.createAlias("user","user");

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("bankPlayProfit"));
        projectionList.add(Projections.sum("triratnaProfit"));
        projectionList.add(Projections.sum("effective"));
        projectionList.add(Projections.sum("bankPlayLose"));
        projectionList.add(Projections.sum("triratnaLose"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        return new TotalPlayerLoseCommand(list);
    }

    @Override
    public TotalTriratna totalTriratna(List<Criterion> criterionList) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Transformers.aliasToBean(TriratnaRepresentation.class));
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }

        //不统计虚拟玩家
        criteriaCount.createAlias("user","user");

        //聚合  统计每个玩家的洗码量
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("bankPair"));
        projectionList.add(Projections.sum("playerPair"));
        projectionList.add(Projections.sum("draw"));
        projectionList.add(Projections.sum("triratnaProfit"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        return new TotalTriratna(list);

    }

    @Override
    public String[] ids(String parentId) {

        String sql = "select b.user_id from t_agent a,t_agent b  where a.user_id = b.parent_id and a.parent_id = '"+parentId+"' \n" +
                "UNION ALL \n" +
                "select user_id from t_agent where parent_id = '"+parentId+"'";

        Query query = getSession().createSQLQuery(sql);
        List list = query.list();

        if(list.size() <1){
            return null;
        }
        String[] strings = new String[list.size()];
        for(int i=0;i<strings.length;i++){
            strings[i] = (String) list.get(i);
        }

        return strings;
    }

    @Override
    public Object[] personalSum(List<Criterion> criterionList) {
        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Criteria.ROOT_ENTITY);
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }
        criteriaCount.createAlias("user","user");

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("player"));
        projectionList.add(Projections.sum("banker"));
        projectionList.add(Projections.sum("playerPair"));
        projectionList.add(Projections.sum("bankPair"));
        projectionList.add(Projections.sum("draw"));
        projectionList.add(Projections.sum("bankPlayLose"));
        projectionList.add(Projections.sum("bankPlayProfit"));
        projectionList.add(Projections.sum("triratnaLose"));
        projectionList.add(Projections.sum("triratnaProfit"));
        projectionList.add(Projections.sum("effective"));

        criteriaCount.setProjection(projectionList);
        List list = criteriaCount.list();

        return (Object[]) list.get(0);
    }

}
