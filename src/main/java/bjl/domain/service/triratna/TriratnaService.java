package bjl.domain.service.triratna;

import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("triratnaService")
public class TriratnaService implements ITriratnaService{

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private IGameDetailedRepository<GameDetailed, String> gameDetailedRepository;

    @Override
    public Pagination<TriratnaRepresentation> pagination(ListITriratnaCommand command) {

        String condition = "";
        String dateStr = "";

        if(command.getStartDate() != null && "".equals(command.getStartDate())){
            if(command.getEndDate() != null && "".equals(command.getEndDate())){
                //时间
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date date = calendar.getTime(); //当天零时零分了零秒的时间

                calendar.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
                Date after = calendar.getTime(); //明天零时零分了零秒的时间

                dateStr += " and t.create_date>='"+CoreDateUtils.formatDateTime(date)+"'" ;
                dateStr += " and t.create_date<'"+CoreDateUtils.formatDateTime(after)+"'";
            }
        }

        if(command.getBoots() != null){
            condition += " and t.boots = "+command.getBoots();
        }
        if(command.getGames() != null){
            condition += " and t.games = "+command.getGames();
        }

        if(!CoreStringUtils.isEmpty(command.getStartDate())){
            dateStr += " and t.create_date>='"+command.getStartDate()+"'" ;
        }
        if(!CoreStringUtils.isEmpty(command.getEndDate())){
            dateStr += " and t.create_date<'"+command.getEndDate()+"'";
        }

        String sql = "SELECT t.boots,t.games,t.lottery,SUM(t.triratna_profit) triratna_profit,SUM(t.bank_pair) bank_pair,SUM(t.player_pair) player_pair,SUM(t.draw) draw "
                     +" FROM t_game_detailed as t,t_user as u where t.user_id = u.id and u.is_virtual <> 1 " + condition + dateStr
                     +" GROUP BY t.boots,t.games,TO_DAYS(t.create_date),t.lottery limit "+(command.getPage()-1)*command.getPageSize()+","+command.getPageSize();

        String countSql = "select count(*) count from (select count(*) count_ "
                +" FROM t_game_detailed as t,t_user as u where t.user_id = u.id and u.is_virtual <> 1 " + condition + dateStr
                +" GROUP BY t.boots,t.games,TO_DAYS(t.create_date),t.lottery ) as a";


        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TriratnaRepresentation.class));
        Query query1 = session.createSQLQuery(countSql).setResultTransformer(Transformers.aliasToBean(TriratnaRepresentation.class));
        TriratnaRepresentation count = (TriratnaRepresentation) query1.uniqueResult();
        List<TriratnaRepresentation> list = query.list();

        return new Pagination<>(list, ((BigInteger) count.getCount()).intValue(),command.getPage(),command.getPageSize());
    }

    @Override
    public TotalTriratna total(ListITriratnaCommand command) {

            List<Criterion> criterionList = new ArrayList<>();

            if(command.getBoots() != null){
                criterionList.add(Restrictions.eq("boots",command.getBoots()));
            }
            if(command.getGames() != null){
                criterionList.add(Restrictions.eq("games",command.getBoots()));
            }

            criterionList.add(Restrictions.ne("user.virtual",1));

        if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))
                || (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss"))) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.lt("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }

        return gameDetailedRepository.totalTriratna(criterionList);

    }
}
