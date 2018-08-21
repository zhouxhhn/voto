package bjl.domain.service.spreadprofit;

import bjl.application.spreadprofit.command.ListSpreadProfitCommand;
import bjl.application.spreadprofit.command.ProfitDetailedCommand;
import bjl.application.spreadprofit.command.SpreadProfitCommand;
import bjl.core.exception.NoFoundException;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.spreadprofit.ISpreadProfitRepository;
import bjl.domain.model.spreadprofit.SpreadProfit;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.User;
import bjl.domain.service.account.IAccountService;
import bjl.domain.service.systemconfig.ISystemConfigService;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/4/16
 */
@Service("spreadProfit")
public class SpreadProfitService implements ISpreadProfitService {

    @Autowired
    private ISpreadProfitRepository<SpreadProfit,String> spreadProfitRepository;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private ISystemConfigService systemConfigService;


    @Override
    public Pagination<SpreadProfit> pagination(ListSpreadProfitCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String,String> aliasMap = new HashMap<>();
        if(!CoreStringUtils.isEmpty(command.getName())){
            criterionList.add(Restrictions.like("account.name",command.getName(), MatchMode.ANYWHERE));
            aliasMap.put("account","account");
        }

        return spreadProfitRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,null,null);
    }

    /**
     * 创建记录
     * @param account
     */
    @Override
    public SpreadProfit create(Account account) {
        SpreadProfit spreadProfit = new SpreadProfit();
        spreadProfit.setAccount(account);
        BigDecimal bigDecimal = BigDecimal.valueOf(0);
        spreadProfit.setYesterdayProfit(bigDecimal);
        spreadProfit.setWeekProfit(bigDecimal);
        spreadProfit.setLastWeekProfit(bigDecimal);
        spreadProfit.setMonthProfit(bigDecimal);
        spreadProfit.setTotalProfit(bigDecimal);
        spreadProfit.setUnsettledProfit(bigDecimal);
        spreadProfit.setReceiveProfit(bigDecimal);
        spreadProfit.setCreateDate(new Date());
        spreadProfit.setTodayProfit(bigDecimal);
        spreadProfitRepository.save(spreadProfit);
        return spreadProfit;
    }


    /**
     * 按用户查
     * @param username
     * @return
     */
    @Override
    public SpreadProfit getByUsername(String username) {

        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("account.userName",username));
        Map<String,String> aliasMap = new HashMap<>();
        aliasMap.put("account","account");
        List<SpreadProfit> list = spreadProfitRepository.list(criterionList,null,null,null,aliasMap);
        if(list.size() < 1){
            //用户之前不存在收益表，则创建
            Account account = accountService.searchByAccountName(username);
            if(account == null){
                throw new NoFoundException();
            }
            return create(account);
        }
        return list.get(0);
    }

    @Override
    public SpreadProfit getByAccount(Account account) {

        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("account",account));
        List<SpreadProfit> list = spreadProfitRepository.list(criterionList,null);
        if(list.size()>0){
            return list.get(0);
        }
        return null;

    }

    /**
     * 下级有效推广人数
     * @param userId
     * @return
     */
    @Override
    public int effective(String userId) {

        return spreadProfitRepository.effective(userId);
    }

    /**
     * 推广总人数
     * @param userId
     * @return
     */
    @Override
    public int total(String userId) {

        return spreadProfitRepository.total(userId);
    }

    @Override
    public void count(Integer type) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if(type == 2){
            calendar.add(Calendar.DATE,-1);
        }
        Date date = calendar.getTime(); //前一天零时零分了零秒的时间
        //微投
        List<SpreadProfitCommand> votes = spreadProfitRepository.vote(date);

        if(votes.size() < 1){
            return;
        }
        //获取推广收益比例
        SystemConfig systemConfig = systemConfigService.get();

//        Map<String,SpreadProfitCommand> map = new HashMap<>();
        //重复的用户，值相加后取唯一值
//        for(SpreadProfitCommand command : votes){
//            if(command.getAccount().getParent() != null){
//                if(command.getTotalBet() == null){
//                    command.setTotalBet(0L);
//                }
//                if(!map.containsKey(command.getAccount().getParent().getUserName())){
//                    map.put(command.getAccount().getUserName(),command);
//                }else {
//                    map.put(command.getAccount().getUserName(),map.get(command.getAccount().getUserName()))
//                            .setTotalBet(command.getTotalBet()+map.get(command.getAccount().getUserName()).getTotalBet());
//                }
//            }
//        }

        Map<String,BigDecimal> userMap = new HashMap<>();
        //遍历
        for (SpreadProfitCommand command : votes) {

            Account parent = command.getAccount().getParent();
            if(parent != null){
                //最低级推广
                BigDecimal totalBet = BigDecimal.valueOf(command.getTotalBet()).multiply(systemConfig.getPump_1()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);

                if(userMap.containsKey(parent.getUserName())){
                    //已记录,则累加
                    userMap.put(parent.getUserName(), userMap.get(parent.getUserName()).add(totalBet));
                }else {
                    //未记录则新增
                    userMap.put(parent.getUserName(), totalBet);
                }
                //上一级推广
                Account parent1 = parent.getParent();
                if(parent1 != null){
                    BigDecimal totalBet1 = BigDecimal.valueOf(command.getTotalBet()).multiply(systemConfig.getPump_2()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);

                    if(userMap.containsKey(parent1.getUserName())){
                        //已记录,则累加
                        userMap.put(parent1.getUserName(), userMap.get(parent1.getUserName()).add(totalBet1));
                    }else {
                        //未记录则新增
                        userMap.put(parent1.getUserName(), totalBet1);
                    }

                    //再上一级推广
                    Account parent2 = parent1.getParent();
                    if(parent2 != null){
                        BigDecimal totalBet2 = BigDecimal.valueOf(command.getTotalBet()).multiply(systemConfig.getPump_3()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);

                        if(userMap.containsKey(parent2.getUserName())){
                            //已记录,则累加
                            userMap.put(parent2.getUserName(), userMap.get(parent2.getUserName()).add(totalBet2));
                        }else {
                            //未记录则新增
                            userMap.put(parent2.getUserName(), totalBet2);
                        }

                        //再上一级推广
                        Account parent3 = parent2.getParent();
                        if(parent3 != null){
                            BigDecimal totalBet3 = BigDecimal.valueOf(command.getTotalBet()).multiply(systemConfig.getPump_4()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);

                            if(userMap.containsKey(parent3.getUserName())){
                                //已记录,则累加
                                userMap.put(parent3.getUserName(), userMap.get(parent3.getUserName()).add(totalBet3));
                            }else {
                                //未记录则新增
                                userMap.put(parent3.getUserName(), totalBet3);
                            }

                            //最上级推广，最多5级
                            Account parent4 = parent3.getParent();
                            if(parent4 != null){
                                BigDecimal totalBet4 = BigDecimal.valueOf(command.getTotalBet()).multiply(systemConfig.getPump_5()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);

                                if(userMap.containsKey(parent4.getUserName())){
                                    //已记录,则累加
                                    userMap.put(parent4.getUserName(), userMap.get(parent4.getUserName()).add(totalBet4));
                                }else {
                                    //未记录则新增
                                    userMap.put(parent4.getUserName(), totalBet4);
                                }
                            }
                        }
                    }

                }
            }
        }
        //遍历所有推广人员，计算收益
        for (Map.Entry<String, BigDecimal> entry : userMap.entrySet()) {

            SpreadProfit spreadProfit = getByUsername(entry.getKey());
            if(type == 1){
                //按小时统计
                spreadProfit.setTodayProfit(spreadProfit.getTodayProfit().add(entry.getValue()));
            }else {
                //按天统计
                spreadProfit.setYesterdayProfit(entry.getValue());
                spreadProfit.setTotalProfit(spreadProfit.getTotalProfit().add(entry.getValue()));
                //今天
                calendar.add(Calendar.DATE,-1);
                if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
                    //如果今天是周一，则结算收益
                    spreadProfit.setWeekProfit(BigDecimal.valueOf(0));
                    spreadProfit.setLastWeekProfit(spreadProfit.getWeekProfit().add(entry.getValue()));
                    spreadProfit.setReceiveProfit(spreadProfit.getLastWeekProfit().add(spreadProfit.getReceiveProfit()));
                    spreadProfit.setUnsettledProfit(BigDecimal.valueOf(0));
                }else {
                    spreadProfit.setWeekProfit(spreadProfit.getWeekProfit().add(entry.getValue()));
                    spreadProfit.setUnsettledProfit(spreadProfit.getUnsettledProfit().add(entry.getValue()));
                }
                //如果今天是本月1号
                if(calendar.get(Calendar.DAY_OF_MONTH) == 1){
                    spreadProfit.setMonthProfit(BigDecimal.valueOf(0));
                }else {
                    spreadProfit.setMonthProfit(spreadProfit.getMonthProfit().add(entry.getValue()));
                }
            }
            spreadProfitRepository.update(spreadProfit);
        }

    }

    @Override
    public JSONObject receive(JSONObject jsonObject) {
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("account.userName",jsonObject.getString("userid")));
        Map<String,String> aliasMap = new HashMap<>();
        aliasMap.put("account","account");
        List<SpreadProfit> list = spreadProfitRepository.list(criterionList,null,null,null,aliasMap);
        if(list.size() < 1){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户收益不存在");
            return jsonObject;
        }
        SpreadProfit spreadProfit = list.get(0);
        User user = userManagerService.searchByAccount(spreadProfit.getAccount());
        if(spreadProfit.getReceiveProfit().compareTo(BigDecimal.valueOf(0)) > 0){
            //更新用户积分
            user.setPrimeScore(user.getPrimeScore().add(spreadProfit.getReceiveProfit()));
            user.setScore(user.getScore().add(spreadProfit.getReceiveProfit()));
            userManagerService.update(user);
            //重置收益
            spreadProfit.setReceiveProfit(BigDecimal.valueOf(0));
            spreadProfitRepository.update(spreadProfit);
            jsonObject.put("code",0);
            jsonObject.put("money",user.getScore());
        }else {
            jsonObject.put("code",1);
            jsonObject.put("errmsg","无收益可领取");
            jsonObject.put("money",user.getScore());
        }
        return jsonObject;
    }

    /**
     * 推广人数
     * @return
     */
    @Override
    public Object[] spreadSum(Integer type,String username) {

        if(type == 1){ // 今日推广人数
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date = calendar.getTime();

            String dateStr = CoreDateUtils.formatDateTime(date);

            return spreadProfitRepository.todaySUm(dateStr,username);
        }else {
            //历史推广人数
            return spreadProfitRepository.todaySUm(null,username);
        }
    }

    /**
     * 今日收益
     * @param username
     * @return
     */
    @Override
    public Object[] todayProfit(String username) {
        Account user = accountService.searchByAccountName(username);

        return spreadProfitRepository.todayProfit(user.getId());
    }

    /**
     * 昨日收益
     * @return
     */
    @Override
    public Object[] yesterdayProfit(String username) {
        Account user = accountService.searchByAccountName(username);

        return spreadProfitRepository.yesterdayProfit(user.getId());
    }

    /**
     * 本周收益
     * @param username
     * @return
     */
    @Override
    public Object[] weekProfit(String username) {
        Account user = accountService.searchByAccountName(username);

        return spreadProfitRepository.weekProfit(user.getId());
    }

    /**
     * 上周收益
     * @param username
     * @return
     */
    @Override
    public Object[] lastWeekProfit(String username) {
        Account user = accountService.searchByAccountName(username);

        return spreadProfitRepository.lastWeekProfit(user.getId());
    }

    @Override
    public Object[] monthProfit(String username) {
        Account user = accountService.searchByAccountName(username);

        return spreadProfitRepository.monthProfit(user.getId());
    }

    @Override
    public List<ProfitDetailedCommand> profitDetailed(String username) {
        Account user = accountService.searchByAccountName(username);

        List<ProfitDetailedCommand> list =  spreadProfitRepository.profitDetailed(user.getId());
        //计算贡献收益
        SystemConfig systemConfig = systemConfigService.get();
        for(ProfitDetailedCommand command : list){
            if("1".equals(command.getLevel())){
                command.setProfit(command.getBet().multiply(systemConfig.getPump_1()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
            }else if("2".equals(command.getLevel())){
                command.setProfit(command.getBet().multiply(systemConfig.getPump_2()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
            }
            else if("3".equals(command.getLevel())){
                command.setProfit(command.getBet().multiply(systemConfig.getPump_3()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
            }
            else if("4".equals(command.getLevel())){
                command.setProfit(command.getBet().multiply(systemConfig.getPump_4()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
            }
            else if("5".equals(command.getLevel())){
                command.setProfit(command.getBet().multiply(systemConfig.getPump_5()).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP));
            }
        }
        return list;
    }

}
