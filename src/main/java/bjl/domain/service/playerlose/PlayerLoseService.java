package bjl.domain.service.playerlose;

import bjl.application.account.IAccountAppService;
import bjl.application.playerlose.command.ListPlayerLoseCommand;
import bjl.application.playerlose.command.TotalPlayerLoseCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
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
@Service("playerLoseService")
public class PlayerLoseService implements IPlayerLoseService{

    @Autowired
    private IGameDetailedRepository<GameDetailed, String> gameDetailedRepository;
    @Autowired
    private IAccountAppService accountAppService;
    @Autowired
    private IUserManagerService userManagerService;

    @Override
    public Pagination<GameDetailed> list(ListPlayerLoseCommand command) {

        List<Criterion> criterionList = criteria(command);
        Map<String, String> aliasMap = new HashMap<>();

        aliasMap.put("user","user");
        if(!CoreStringUtils.isEmpty(command.getName())){
            aliasMap.put("user.account","account");
        }
//        List<Order> orderList = new ArrayList<>();
//        orderList.add(Order.desc("createDate"));

        return gameDetailedRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,null,null);
    }

    /**
     * 合计
     * @param command
     * @return
     */
    @Override
    public TotalPlayerLoseCommand count(ListPlayerLoseCommand command) {
        return gameDetailedRepository.count(criteria(command),command.getName());
    }


    private List<Criterion> criteria(ListPlayerLoseCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if(command.getBoots() != null){
            criterionList.add(Restrictions.eq("boots",command.getBoots()));
        }
        if(command.getGames() != null){
            criterionList.add(Restrictions.eq("games",command.getGames()));
        }

        criterionList.add(Restrictions.ne("user.virtual",1));

        if(!CoreStringUtils.isEmpty(command.getName())){
            criterionList.add(Restrictions.like("account.name",command.getName(),MatchMode.ANYWHERE));
        }

        //不统计虚拟玩家
        criterionList.add(Restrictions.eq("user.virtual",2));

        if(!CoreStringUtils.isEmpty(command.getStartDate()) || !CoreStringUtils.isEmpty(command.getEndDate())){
            if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))
                    || (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss"))) {
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
            Date after = calendar.getTime(); //下一天零时零分了零秒的时间

            criterionList.add(Restrictions.ge("createDate",date));
            criterionList.add(Restrictions.lt("createDate",after));
        }
        return criterionList;
    }
}
