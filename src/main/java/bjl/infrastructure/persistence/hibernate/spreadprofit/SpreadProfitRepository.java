package bjl.infrastructure.persistence.hibernate.spreadprofit;

import bjl.application.spreadprofit.command.ProfitDetailedCommand;
import bjl.application.spreadprofit.command.SpreadProfitCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.spreadprofit.ISpreadProfitRepository;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/4/16
 */
@Repository("spreadProfitRepository")
public class SpreadProfitRepository extends AbstractHibernateGenericRepository<SpreadProfit,String>
        implements ISpreadProfitRepository<SpreadProfit,String> {


    @Override
    public int total(String id) {

        String sql = "SELECT COUNT(*) FROM t_account u where u.parent_id = '"+id+"'";
        Query query = getSession().createSQLQuery(sql);
        List list = query.list();
        Object object =  list.get(0);
        return ((BigInteger) object).intValue();
    }

    @Override
    public int effective(String userId) {

        String sql = "select count(*) from \n" +
                "(select r.account_id from t_recharge r,t_account u where r.account_id = u.id and r.is_success =1 and u.parent_id='"+userId+"' GROUP BY r.account_id) as a";
        Query query = getSession().createSQLQuery(sql);
        List list = query.list();
        Object object =  list.get(0);
        return ((BigInteger) object).intValue();
    }

    /**
     * 微投游戏
     * @param date
     * @return
     */
    @Override
    public List<SpreadProfitCommand> vote(Date date) {

        Criteria criteriaCount = getSession().createCriteria(GameDetailed.class);
        criteriaCount.setResultTransformer(Transformers.aliasToBean(SpreadProfitCommand.class));

        criteriaCount.add(Restrictions.ge("createDate",date));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.sum("effective"));
        //分组
        projectionList.add(Projections.groupProperty("user"));
        criteriaCount.setProjection(projectionList);

        List list = criteriaCount.list();
        List<SpreadProfitCommand> list1 = new ArrayList<>();
        for(Object object : list){
            Object[] objects = (Object[]) object;
            list1.add(new SpreadProfitCommand(objects));
        }

        return list1;
    }

    @Override
    public Object[] todaySUm(String dateStr,String username) {

        if(dateStr != null){
            dateStr = " and u.create_date>='"+dateStr+"'";
        }else {
            dateStr = "";
        }
        BigInteger[] objects = new BigInteger[5];
        //一级人数
        String sql1 = "select count(*) from t_account u,t_account a WHERE u.parent_id = a.id "+dateStr+" and a.user_name ='"+username+"'";
        Query q1 = getSession().createSQLQuery(sql1);
        List list = q1.list();

        objects[0] = (BigInteger) list.get(0);

        //二级人数

        String sql2 = "select count(*) from t_account a,\n" +
                "(select u.id from t_account u,t_account a WHERE u.parent_id = a.id "+dateStr+" and a.user_name ='"+username+"' ) as b\n" +
                "where a.parent_id = b.id ";
        Query q2 = getSession().createSQLQuery(sql2);
        List list2 = q2.list();

        objects[1] = (BigInteger) list2.get(0);

        //三级人数
        String sql3 = "SELECT count(*) from t_account as c,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u,t_account a WHERE u.parent_id = a.id "+dateStr+" and a.user_name ='"+username+"') as b\n" +
                "where a.parent_id = b.id) as b where c.parent_id = b.id";
        Query q3 = getSession().createSQLQuery(sql3);
        List list3 = q3.list();

        objects[2] = (BigInteger) list3.get(0);

        //四级人数
        String sql4 = "SELECT count(*) from t_account as d,\n" +
                "(SELECT c.id from t_account as c,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u,t_account a WHERE u.parent_id = a.id "+dateStr+" and a.user_name ='"+username+"') as b\n" +
                "where a.parent_id = b.id) as b where c.parent_id = b.id) as b where d.parent_id = b.id";
        Query q4 = getSession().createSQLQuery(sql4);
        List list4 = q4.list();

        objects[3] = (BigInteger) list4.get(0);
        //五级人数
        String sql5 = "select count(*) from t_account as e,\n" +
                "(SELECT d.id from t_account as d,\n" +
                "(SELECT c.id from t_account as c,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u,t_account a WHERE u.parent_id = a.id "+dateStr+" and a.user_name ='"+username+ "') as b\n" +
                "where a.parent_id = b.id) as b where c.parent_id = b.id) as b where d.parent_id = b.id) as b WHERE b.id = e.parent_id";
        Query q5 = getSession().createSQLQuery(sql5);
        List list5 = q5.list();

        objects[4] = (BigInteger) list5.get(0);

        return objects;
    }

    @Override
    public Object[] todayProfit(String id) {

        Object[] objects = new Object[5];
        //一级收益
        String sql1 = "select sum(s.today_profit) from t_account u,t_spread_profit s WHERE  u.id=s.account_id and u.parent_id ='"+id+"'";
        Query q1 = getSession().createSQLQuery(sql1);
        List list = q1.list();

        objects[0] =  (list.get(0) == null ? 0 : list.get(0)) ;

        //二级收益

        String sql2 = "select sum(s.today_profit) from t_account a,t_spread_profit s,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id and a.id = s.account_id";
        Query q2 = getSession().createSQLQuery(sql2);
        List list2 = q2.list();

        objects[1] = list2.get(0) == null ? 0 : list2.get(0) ;

        //三级收益
        String sql3 = "select sum(s.today_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id and a.id = s.account_id";
        Query q3 = getSession().createSQLQuery(sql3);
        List list3 = q3.list();

        objects[2] =  list3.get(0) == null ? 0 : list3.get(0) ;

        //四级收益
        String sql4 = "select sum(s.today_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q4 = getSession().createSQLQuery(sql4);
        List list4 = q4.list();

        objects[3] =  list4.get(0) == null ? 0 : list4.get(0) ;
        //五级收益
        String sql5 = "select sum(s.today_profit) FROM t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q5 = getSession().createSQLQuery(sql5);
        List list5 = q5.list();

        objects[4] =  list5.get(0) == null ? 0 : list5.get(0) ;

        return objects;
    }

    @Override
    public Object[] yesterdayProfit(String id) {

        Object[] objects = new Object[5];
        //一级昨日收益
        String sql1 = "select sum(s.yesterday_profit) from t_account u,t_spread_profit s WHERE  u.id=s.account_id and u.parent_id ='"+id+"'";
        Query q1 = getSession().createSQLQuery(sql1);
        List list = q1.list();

        objects[0] =  (list.get(0) == null ? 0 : list.get(0)) ;

        //二级昨日收益

        String sql2 = "select sum(s.yesterday_profit) from t_account a,t_spread_profit s,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id and a.id = s.account_id";
        Query q2 = getSession().createSQLQuery(sql2);
        List list2 = q2.list();

        objects[1] = list2.get(0) == null ? 0 : list2.get(0) ;

        //三级昨日收益
        String sql3 = "select sum(s.yesterday_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id and a.id = s.account_id";
        Query q3 = getSession().createSQLQuery(sql3);
        List list3 = q3.list();

        objects[2] =  list3.get(0) == null ? 0 : list3.get(0) ;

        //四级昨日收益
        String sql4 = "select sum(s.yesterday_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q4 = getSession().createSQLQuery(sql4);
        List list4 = q4.list();

        objects[3] =  list4.get(0) == null ? 0 : list4.get(0) ;
        //五级昨日收益
        String sql5 = "select sum(s.yesterday_profit) FROM t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q5 = getSession().createSQLQuery(sql5);
        List list5 = q5.list();

        objects[4] =  list5.get(0) == null ? 0 : list5.get(0) ;

        return objects;

    }

    @Override
    public Object[] weekProfit(String id) {

        Object[] objects = new Object[5];
        //一级本周收益
        String sql1 = "select sum(s.week_profit) from t_account u,t_spread_profit s WHERE  u.id=s.account_id and u.parent_id ='"+id+"'";
        Query q1 = getSession().createSQLQuery(sql1);
        List list = q1.list();

        objects[0] =  (list.get(0) == null ? 0 : list.get(0)) ;

        //二级本周收益

        String sql2 = "select sum(s.week_profit) from t_account a,t_spread_profit s,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id and a.id = s.account_id";
        Query q2 = getSession().createSQLQuery(sql2);
        List list2 = q2.list();

        objects[1] = list2.get(0) == null ? 0 : list2.get(0) ;

        //三级本周收益
        String sql3 = "select sum(s.week_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id and a.id = s.account_id";
        Query q3 = getSession().createSQLQuery(sql3);
        List list3 = q3.list();

        objects[2] =  list3.get(0) == null ? 0 : list3.get(0) ;

        //四级本周收益
        String sql4 = "select sum(s.week_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q4 = getSession().createSQLQuery(sql4);
        List list4 = q4.list();

        objects[3] =  list4.get(0) == null ? 0 : list4.get(0) ;
        //五级本周收益
        String sql5 = "select sum(s.week_profit) FROM t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q5 = getSession().createSQLQuery(sql5);
        List list5 = q5.list();

        objects[4] =  list5.get(0) == null ? 0 : list5.get(0) ;

        return objects;

    }

    @Override
    public Object[] lastWeekProfit(String id) {
        Object[] objects = new Object[5];
        //一级上周收益
        String sql1 = "select sum(s.last_week_profit) from t_account u,t_spread_profit s WHERE  u.id=s.account_id and u.parent_id ='"+id+"'";
        Query q1 = getSession().createSQLQuery(sql1);
        List list = q1.list();

        objects[0] =  (list.get(0) == null ? 0 : list.get(0)) ;

        //二级上周收益

        String sql2 = "select sum(s.last_week_profit) from t_account a,t_spread_profit s,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id and a.id = s.account_id";
        Query q2 = getSession().createSQLQuery(sql2);
        List list2 = q2.list();

        objects[1] = list2.get(0) == null ? 0 : list2.get(0) ;

        //三级上周收益
        String sql3 = "select sum(s.last_week_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id and a.id = s.account_id";
        Query q3 = getSession().createSQLQuery(sql3);
        List list3 = q3.list();

        objects[2] =  list3.get(0) == null ? 0 : list3.get(0) ;

        //四级上周收益
        String sql4 = "select sum(s.last_week_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q4 = getSession().createSQLQuery(sql4);
        List list4 = q4.list();

        objects[3] =  list4.get(0) == null ? 0 : list4.get(0) ;
        //五级上周收益
        String sql5 = "select sum(s.last_week_profit) FROM t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q5 = getSession().createSQLQuery(sql5);
        List list5 = q5.list();

        objects[4] =  list5.get(0) == null ? 0 : list5.get(0) ;

        return objects;
    }

    @Override
    public Object[] monthProfit(String id) {
        Object[] objects = new Object[5];
        //一级本月收益
        String sql1 = "select sum(s.month_profit) from t_account u,t_spread_profit s WHERE  u.id=s.account_id and u.parent_id ='"+id+"'";
        Query q1 = getSession().createSQLQuery(sql1);
        List list = q1.list();

        objects[0] =  (list.get(0) == null ? 0 : list.get(0)) ;

        //二级本月收益

        String sql2 = "select sum(s.month_profit) from t_account a,t_spread_profit s,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id and a.id = s.account_id";
        Query q2 = getSession().createSQLQuery(sql2);
        List list2 = q2.list();

        objects[1] = list2.get(0) == null ? 0 : list2.get(0) ;

        //三级本月收益
        String sql3 = "select sum(s.month_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id and a.id = s.account_id";
        Query q3 = getSession().createSQLQuery(sql3);
        List list3 = q3.list();

        objects[2] =  list3.get(0) == null ? 0 : list3.get(0) ;

        //四级本月收益
        String sql4 = "select sum(s.month_profit) from t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q4 = getSession().createSQLQuery(sql4);
        List list4 = q4.list();

        objects[3] =  list4.get(0) == null ? 0 : list4.get(0) ;
        //五级本月收益
        String sql5 = "select sum(s.month_profit) FROM t_account a,t_spread_profit s,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select u.id from t_account u WHERE u.parent_id  ='"+id+"') as b\n" +
                "where a.parent_id = b.id) as b where a.parent_id = b.id) as b WHERE a.parent_id = b.id) as b WHERE a.parent_id = b.id and a.id = s.account_id";
        Query q5 = getSession().createSQLQuery(sql5);
        List list5 = q5.list();

        objects[4] =  list5.get(0) == null ? 0 : list5.get(0) ;

        return objects;
    }

    @Override
    public List<ProfitDetailedCommand> profitDetailed(String id) {

        String sql = "select a.`name`,'1' as level,sum(m.effective) as bet from t_account a,t_user u,t_game_detailed m \n" +
                "WHERE a.id=u.account_id and u.id = m.user_id and u.is_virtual =2 and a.parent_id='"+id+"' GROUP BY m.user_id\n" +
                "union all \n" +
                "select a.`name`,'2' as level,sum(m.effective) as bet from t_account a,t_user u,t_game_detailed m,\n" +
                "(select a.id from t_account a where a.parent_id='"+id+"') as b \n" +
                "WHERE a.parent_id=b.id and a.id=u.account_id and u.id = m.user_id and u.is_virtual =2 GROUP BY m.user_id\n" +
                "union all \n" +
                "select a.`name`,'3' as level,sum(m.effective) as bet from t_account a,t_user u,t_game_detailed m,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a where a.parent_id='"+id+"') as b \n" +
                "where a.parent_id = b.id) as b \n" +
                "WHERE a.parent_id=b.id and a.id=u.account_id and u.id = m.user_id and u.is_virtual =2 GROUP BY m.user_id\n" +
                "union all \n" +
                "select a.`name`,'4' as level,sum(m.effective) as bet from t_account a,t_user u,t_game_detailed m,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a where a.parent_id='"+id+"') as b \n" +
                "where a.parent_id = b.id) as b where a.parent_id=b.id) as b \n" +
                "WHERE a.parent_id=b.id and a.id=u.account_id and u.id = m.user_id and u.is_virtual =2 GROUP BY m.user_id\n" +
                "union all \n" +
                "select a.`name`,'5' as level,sum(m.effective) as bet from t_account a,t_user u,t_game_detailed m,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a,\n" +
                "(select a.id from t_account a where a.parent_id='"+id+"') as b \n" +
                "where a.parent_id = b.id) as b where a.parent_id=b.id) as b where a.parent_id = b.id ) as b \n" +
                "WHERE a.parent_id=b.id and a.id=u.account_id and u.id = m.user_id and u.is_virtual =2 GROUP BY m.user_id";

        Query query = getSession().createSQLQuery(sql);
        List list =query.list();
        List<ProfitDetailedCommand> list1 = new ArrayList<>();
        for(Object object : list){
            list1.add(new ProfitDetailedCommand(object));
        }
        return list1;
    }

}
