package bjl.domain.service.gamedetailed;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.financialSummary.IFinancialSummaryAppService;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.scoredetailed.IScoreDetailedAppService;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/1/3.
 */
@Service("gameDetailedService")
public class GameDetailedService implements IGameDetailedService{

    @Autowired
    private IGameDetailedRepository<GameDetailed,String> gameDetailedRepository;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IUserRepository<User, String> userRepository;
    @Autowired
    private IScoreDetailedAppService scoreDetailedAppService;
    @Autowired
    private IScoreDetailedRepository<ScoreDetailed, String> scoreDetailedRepository;

    @Override
    public User save(CreateGameDetailedCommand command) {

        try {

            User user = userManagerService.searchByUsername(command.getUser());

            if (user == null) {
                return null;
            }

            if (command.getWinTotal().compareTo(BigDecimal.valueOf(0)) > 0) {

                BigDecimal newScore = user.getScore().add(command.getWinTotal());

                //更新用户积分
                user.setScore(newScore);
                userManagerService.update(user);

                //增加用户积分变更记录
                ScoreDetailed scoreDetailed = new ScoreDetailed();
                scoreDetailed.setCreateDate(new Date());
                scoreDetailed.setActionType(2);
                scoreDetailed.setScore(command.getWinTotal());
                scoreDetailed.setNewScore(newScore);
                scoreDetailed.setUser(user);
                scoreDetailedRepository.save(scoreDetailed);
            }

            //添加游戏明细
            GameDetailed gameDetailed = new GameDetailed();
            gameDetailed.setBanker(command.getBanker());
            gameDetailed.setBankPair(command.getBankPair());
            gameDetailed.setBankPlayLose(command.getBankPlayLose());
            gameDetailed.setBankPlayProfit(command.getBankPlayProfit());
            gameDetailed.setBoots(command.getBoots());
            gameDetailed.setDraw(command.getDraw());
            gameDetailed.setEffective(command.getEffective());
            gameDetailed.setGames(command.getGames());
            gameDetailed.setHallType(command.getHallType());
            gameDetailed.setLottery(command.getLottery());
            gameDetailed.setPlayer(command.getPlayer());
            gameDetailed.setPlayerPair(command.getPlayerPair());
            gameDetailed.setTriratnaLose(command.getTriratnaLose());
            gameDetailed.setTriratnaProfit(command.getTriratnaProfit());
            gameDetailed.setRestScore(user.getScore());
            gameDetailed.setInitScore(command.getInitScore());
            gameDetailed.setCreateDate(new Date());
            gameDetailed.setUser(user);
            gameDetailedRepository.save(gameDetailed);

            return user;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(GameDetailed gameDetailed,BigDecimal changeScore) {

        try {
            User user = gameDetailed.getUser();

            if(changeScore.compareTo(BigDecimal.valueOf(0)) != 0){

                BigDecimal newScore = user.getScore().add(changeScore);

                if(newScore.compareTo(BigDecimal.valueOf(0)) < 0){
                    //积分最低为0
                    newScore = BigDecimal.valueOf(0);
                }
                //更新用户积分
                user.setScore(newScore);
                userManagerService.update(user);

                //增加用户积分变更记录
                ScoreDetailed scoreDetailed = new ScoreDetailed();
                scoreDetailed.setCreateDate(new Date());
                scoreDetailed.setActionType(2);
                scoreDetailed.setScore(changeScore);
                scoreDetailed.setNewScore(newScore);
                scoreDetailed.setUser(user);
                scoreDetailedRepository.save(scoreDetailed);

                //更新游戏明细
                gameDetailedRepository.update(gameDetailed);
            }

            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新玩家游戏记录数据
     * @param command
     * @param changeScore
     * @return
     */
    @Override
    public User update(CreateGameDetailedCommand command, BigDecimal changeScore) {

        List<Criterion> criterionList = new ArrayList<>();
        //房间类型
        criterionList.add(Restrictions.eq("hallType",command.getHallType()));
        //鞋数
        criterionList.add(Restrictions.eq("boots",command.getBoots()));
        //局数
        criterionList.add(Restrictions.eq("games",command.getGames()));

        Map<String,String> aliasMap = new HashMap<>();
        aliasMap.put("user","user");
        aliasMap.put("user.account","account");
        criterionList.add(Restrictions.eq("account.userName",command.getUser()));

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        //先取玩家原游戏记录数据
        List<GameDetailed> list = gameDetailedRepository.list(criterionList,orderList,null,null,aliasMap);

        GameDetailed gameDetailed = list.get(0);

        User user = gameDetailed.getUser();
        //用户积分是否变动，未变动则无更新
        if(changeScore.compareTo(BigDecimal.valueOf(0)) != 0){

            BigDecimal newScore = user.getScore().add(changeScore);

            if(newScore.compareTo(BigDecimal.valueOf(0)) < 0){
                //积分最低为0
                newScore = BigDecimal.valueOf(0);
            }
            //更新用户积分
            user.setScore(newScore);
            userManagerService.update(user);

            //增加用户积分变更记录
            ScoreDetailed scoreDetailed = new ScoreDetailed();
            scoreDetailed.setCreateDate(new Date());
            scoreDetailed.setActionType(2);
            scoreDetailed.setScore(changeScore);
            scoreDetailed.setNewScore(newScore);
            scoreDetailed.setUser(user);
            scoreDetailedRepository.save(scoreDetailed);

            //更新游戏明细
            gameDetailed.setLottery(command.getLottery());
            gameDetailed.setTriratnaLose(command.getTriratnaLose());
            gameDetailed.setBankPlayLose(command.getBankPlayLose());
            gameDetailed.setBankPlayProfit(command.getBankPlayProfit());
            gameDetailed.setTriratnaProfit(command.getTriratnaProfit());
            gameDetailed.setRestScore(user.getScore());
            gameDetailed.setInitScore(user.getPrimeScore());

            gameDetailedRepository.update(gameDetailed);
        }

        return user;
    }

    /**
     * 获取表数据
     * @param strings
     */
    @Override
    public List<GameDetailed> apiList(String[] strings) {

        List<Criterion> criterionList = new ArrayList<>();
        //房间类型
        criterionList.add(Restrictions.eq("hallType",Integer.valueOf(strings[1])));
        //鞋数
        criterionList.add(Restrictions.eq("boots",Integer.valueOf(strings[2])));
        //局数
        criterionList.add(Restrictions.eq("games",Integer.valueOf(strings[3])));
        //时间
        Date date = CoreDateUtils.parseDate(strings[0], "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);   //设置当前日期
        c.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        Date after = c.getTime(); //结果

        criterionList.add(Restrictions.ge("createDate",date));
        criterionList.add(Restrictions.lt("createDate",after));
        return gameDetailedRepository.list(criterionList,null);
    }

    /**
     * 输赢明细
     * @param command
     * @return
     */
    @Override
    public Pagination<GameDetailed> pagination(ListGameDetailedCommand command) {

//        List<Order> orderList = new ArrayList<>();
//        orderList.add(Order.desc("createDate"));

        Map<String ,String> alisMap = new HashMap<>();
        alisMap.put("user","user");
        if(!CoreStringUtils.isEmpty(command.getParentId())){
            alisMap.put("user.account","account");
        }
        List<Criterion> list = criteria(command);
        list.add(Restrictions.ne("user.virtual",1));
        return gameDetailedRepository.pagination(command.getPage(),command.getPageSize(),list,alisMap,null,null);
    }

    /**
     * 合计
     * @param command
     * @return
     */
    @Override
    public TotalGameDetailedCommand total(ListGameDetailedCommand command) {

        List<Criterion> list = criteria(command);
        list.add(Restrictions.ne("user.virtual",1));
        return gameDetailedRepository.total(list,command.getParentId());
    }

    /**
     * 查询当天最大鞋局
     * @param roomType
     * @return
     */
    @Override
    public List<GameDetailed> getMax(Integer roomType) {
        ListGameDetailedCommand command =  new ListGameDetailedCommand();
        command.setHallType(roomType);

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return gameDetailedRepository.list(criteria(command),orderList);
    }

    /**
     * 统计
     * @return
     */
    @Override
    public List<CountGameDetailedCommand> list(Date date) {
        return gameDetailedRepository.count(date);
    }

    /**
     * 查询最后一局记录
     * @param roomType
     * @return
     */
    @Override
    public List<GameDetailed> getLast(Integer roomType,Integer boots, Integer games) {

        //按鞋局查当局有所玩家记录
        ListGameDetailedCommand command = new ListGameDetailedCommand();
        command.setHallType(roomType);
        command.setBoots(boots);
        command.setGames(games);
        return gameDetailedRepository.list(criteria(command),null);

    }


    private List<Criterion> criteria(ListGameDetailedCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if(command.getBoots() != null){
            criterionList.add(Restrictions.eq("boots",command.getBoots()));
        }
        if(command.getGames() != null){
            criterionList.add(Restrictions.eq("games",command.getGames()));
        }
        if(command.getHallType() != null && command.getHallType() != 0){
            criterionList.add(Restrictions.eq("hallType",command.getHallType()));
        }

        if(!CoreStringUtils.isEmpty(command.getParentId())){
            //子查询
            String[] strings = gameDetailedRepository.ids(command.getParentId());
            if(strings != null){
                criterionList.add(Restrictions.in("account.id",strings));
            }else {
                strings = new String[]{"1"};
                criterionList.add(Restrictions.in("account.id",strings));
            }

        }

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
