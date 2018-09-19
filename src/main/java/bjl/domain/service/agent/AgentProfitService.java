package bjl.domain.service.agent;

import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.agent.AgentProfit;
import bjl.domain.model.agent.IAgentProfitRepository;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("agentProfitService")
public class AgentProfitService implements IAgentProfitService{

    @Autowired
    private IAgentProfitRepository<AgentProfit,String> agentProfitRepository;

    /**
     * 创建记录
     * @param agentProfit
     */
    @Override
    public void create(AgentProfit agentProfit) {

        agentProfit.setCreateDate(new Date());
        agentProfitRepository.save(agentProfit);
    }

    /**
     * 分页条件查询
     * @param command
     * @return
     */
    @Override
    public Pagination<AgentProfit> pagination(ListAgentProfitCommand command) {

        Map<String, String> aliasMap = new HashMap<>();
        if(!CoreStringUtils.isEmpty(command.getPlayerName())){
            aliasMap.put("play","play");
        }
        if(!CoreStringUtils.isEmpty(command.getFirstId()) || !CoreStringUtils.isEmpty(command.getFirstName())){
            aliasMap.put("firstAgent","firstAgent");
        }
        if(!CoreStringUtils.isEmpty(command.getSecondId()) || !CoreStringUtils.isEmpty(command.getSecondName())){
            aliasMap.put("secondAgent","secondAgent");
        }

//        List<Order> orderList = new ArrayList<>();
//        orderList.add(Order.desc("createDate"));

        return agentProfitRepository.pagination(command.getPage(),command.getPageSize(),criteria(command),aliasMap,null,null);
    }

    private List<Criterion> criteria(ListAgentProfitCommand command) {

        List<Criterion> criterionList = new ArrayList<>();

        if(!CoreStringUtils.isEmpty(command.getPlayerName())){
            criterionList.add(Restrictions.like("play.name",command.getPlayerName(), MatchMode.ANYWHERE));
        }
        if(!CoreStringUtils.isEmpty(command.getFirstName())){
            criterionList.add(Restrictions.or(Restrictions.like("firstAgent.name",command.getFirstName(), MatchMode.ANYWHERE)));
        }
        if(!CoreStringUtils.isEmpty(command.getSecondName())){
            criterionList.add(Restrictions.or(Restrictions.like("secondAgent.name",command.getSecondName(), MatchMode.ANYWHERE)));
        }

        if(!CoreStringUtils.isEmpty(command.getFirstId())){
            criterionList.add(Restrictions.eq("firstAgent.id",command.getFirstId()));
        }

        if(!CoreStringUtils.isEmpty(command.getSecondId())){
            criterionList.add(Restrictions.eq("secondAgent.id",command.getSecondId()));
        }

        if(!CoreStringUtils.isEmpty(command.getStartDate()) || !CoreStringUtils.isEmpty(command.getEndDate())){
            if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))) {
                criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
            }
            if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
                criterionList.add(Restrictions.lt("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
            }
        }else {
            //时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date = calendar.getTime(); //当天零时零分了零秒的时间

            calendar.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
            Date after = calendar.getTime(); //上一天零时零分了零秒的时间

            criterionList.add(Restrictions.ge("createDate",date));
            criterionList.add(Restrictions.lt("createDate",after));
        }
        return criterionList;
    }

    @Override
    public Pagination<AgentProfit> exportList(ListAgentProfitCommand command) {

        Map<String, String> aliasMap = new HashMap<>();
        if(!CoreStringUtils.isEmpty(command.getPlayerName())){
            aliasMap.put("play","play");
        }
        if(!CoreStringUtils.isEmpty(command.getFirstId()) || !CoreStringUtils.isEmpty(command.getFirstName())){
            aliasMap.put("firstAgent","firstAgent");
        }
        if(!CoreStringUtils.isEmpty(command.getSecondId()) || !CoreStringUtils.isEmpty(command.getSecondName())){
            aliasMap.put("secondAgent","secondAgent");
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return agentProfitRepository.pagination(command.getPage(),command.getPageSize(),criteria(command),aliasMap,orderList,null);
    }
}
