package bjl.domain.service.scoredetailed;

import com.alibaba.fastjson.JSONObject;

import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.scoredetailed.IScoreDetailedAppService;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("scoreDetailedService")
public class ScoreDetailedService implements IScoreDetailedService{

    @Autowired
    private IScoreDetailedRepository<ScoreDetailed, String> scoreDetailedRepository;
    @Autowired
    private IUserRepository<User,String> userRepository;
    @Autowired
    private IUserManagerService userManagerService;

    /**
     * 用户积分变更
     * @param command
     * @return
     */
    @Override
    public User create(CreateScoreDetailedCommand command) {

        try {
            User user = userManagerService.searchByUsername(command.getUserId());

            if (user == null){
                return null;
            }
            BigDecimal newScore = user.getScore().add(command.getScore());
            if(newScore.compareTo(BigDecimal.valueOf(0)) <0 ){
                //用户积分不足
                return null;
            }
            //先更新用户积分
//            user.setTotalScore(newScore);
            user.setScore(newScore);
//            user.setDateScore(user.getDateScore().add(command.getScore()));
            userRepository.save(user);
            //增加用户积分变更记录
            ScoreDetailed scoreDetailed = new ScoreDetailed();
            scoreDetailed.setCreateDate(new Date());
            scoreDetailed.setActionType(command.getActionType());
            scoreDetailed.setScore(command.getScore());
            scoreDetailed.setNewScore(newScore);
            scoreDetailed.setUser(user);
            scoreDetailedRepository.save(scoreDetailed);
            return user;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新用户【"+command.getUserId()+"】积分异常..........");
        }
        return null;
    }

    /**
     * 个人流水
     * @param command
     * @return
     */
    @Override
    public Pagination<ScoreDetailed> pagination(ListGameDetailedCommand command) {
        Map<String ,String> alisMap = new HashMap<>();
        alisMap.put("user","user");

        List<Criterion> list = criteria(command);
        Pagination<ScoreDetailed> pagination =  scoreDetailedRepository.pagination(command.getPage(),command.getPageSize(),list,alisMap,null,null);
        return pagination;
    }

    private List<Criterion> criteria(ListGameDetailedCommand command) {
        List<Criterion> criterionList = new ArrayList<>();

        if(command.getToken() != null && !"".equals(command.getToken())){
            criterionList.add(Restrictions.eq("user.id",command.getToken()));
        }

        //
        if(command.getTimeType() != null){
            Date dt = new Date();


            //表示前三天
            if(2 == command.getTimeType()){
                Date date=new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -3);
                dt = calendar.getTime();

            }
            //表示本周
            if(3 == command.getTimeType()){
                Calendar cal = Calendar.getInstance();
                cal.setTime(dt);
                // 获得当前日期是一个星期的第几天
                int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (1 == dayWeek) {
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                }
                // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
                cal.setFirstDayOfWeek(Calendar.MONDAY);
                // 获得当前日期是一个星期的第几天
                int day = cal.get(Calendar.DAY_OF_WEEK);
                // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
                cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
                dt = cal.getTime();

            }
            //开始时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date = calendar.getTime(); //当天零时零分了零秒的时间

            //结束时间
            calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
            Date after = calendar.getTime(); //下一天零时零分了零秒的时间

            criterionList.add(Restrictions.ge("createDate",date));
            criterionList.add(Restrictions.lt("createDate",after));
        }

        return criterionList;

    }

}
