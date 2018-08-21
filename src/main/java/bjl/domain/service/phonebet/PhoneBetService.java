package bjl.domain.service.phonebet;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.application.phonebet.command.ListPhoneBetCommand;
import bjl.application.scoredetailed.IScoreDetailedAppService;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.phonebet.IPhoneBetRepository;
import bjl.domain.model.phonebet.PhoneBet;
import bjl.domain.model.user.User;
import bjl.domain.service.account.IAccountService;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/1/9.
 */
@Service("phoneBetService")
public class PhoneBetService implements IPhoneBetService{

    @Autowired
    private IPhoneBetRepository<PhoneBet, String> phoneBetRepository;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IScoreDetailedAppService scoreDetailedAppService;

    /**
     * 创建电话投注
     */
    @Override
    public JSONObject create(JSONObject jsonObject) {

        User user = userManagerService.searchByUsername(jsonObject.getString("userid"));
        if(user == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        BigDecimal score = jsonObject.getBigDecimal("score");
        if(user.getScore().compareTo(score) <0){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","积分不足");
            return jsonObject;
        }
        //修改用户资金
        CreateScoreDetailedCommand scoreDetailedCommand = new CreateScoreDetailedCommand(jsonObject.getString("userid"),score.multiply(BigDecimal.valueOf(-1)),3);
        scoreDetailedAppService.create(scoreDetailedCommand);

        PhoneBet phoneBet = new PhoneBet();
        phoneBet.setFrozenScore(score);
        phoneBet.setRestScore(score);
        phoneBet.setHall(jsonObject.getInteger("index"));
        phoneBet.setLoseScore(BigDecimal.valueOf(0));
        phoneBet.setPhoneNo(jsonObject.getString("telephone"));
        phoneBet.setStatus(1);
        phoneBet.setUser(user);
        phoneBet.setCreateDate(new Date());
        phoneBetRepository.save(phoneBet);

        jsonObject.put("code",0);
        jsonObject.put("errmsg","电话投注成功");
        return jsonObject;
    }

    /**
     * 分页条件查询
     * @param command
     * @return
     */
    @Override
    public Pagination<PhoneBet> pagination(ListPhoneBetCommand command) {

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return phoneBetRepository.pagination(command.getPage(),command.getPageSize(),criteria(command),orderList);
    }

    /**
     * 合计
     * @return
     */
    @Override
    public Object[] total(ListPhoneBetCommand command) {

        return phoneBetRepository.total(criteria(command));
    }

    /**
     * 开工
     * @param id id
     * @param jobNum 工号
     */
    @Override
    public void jobStart(String id, String jobNum) {

        PhoneBet phoneBet = phoneBetRepository.getById(id);
        if(phoneBet != null){
            phoneBet.setStartDate(new Date());
            phoneBet.setJobNum(jobNum);
            phoneBet.setStatus(2);
            phoneBetRepository.update(phoneBet);
        }

    }

    /**
     * 停工
     */
    @Override
    public void jobEnd(String id, BigDecimal score,BigDecimal lose) {

        PhoneBet phoneBet = phoneBetRepository.getById(id);
        if(phoneBet != null){
            phoneBet.setStatus(3);
            phoneBet.setEndDate(new Date());
            phoneBet.setRestScore(score);
            phoneBet.setLoseScore(lose);

            User user = phoneBet.getUser();
            if(score.compareTo(BigDecimal.valueOf(0))>0){
                //更新用户积分
                CreateScoreDetailedCommand scoreDetailedCommand  = new CreateScoreDetailedCommand(user.getAccount().getUserName(),score,3);
                scoreDetailedAppService.create(scoreDetailedCommand);
            }
            phoneBetRepository.update(phoneBet);
        }

    }

    /**
     * 按用户查询
     * @param username
     * @return
     */
    @Override
    public PhoneBet searchByUsername(String username) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        User user = userManagerService.searchByUsername(username);
        if(!CoreStringUtils.isEmpty(username)){
            criterionList.add(Restrictions.eq("user",user));
        }
        criterionList.add(Restrictions.or(Restrictions.eq("status",1),Restrictions.eq("status",2)));
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        List<PhoneBet> list = phoneBetRepository.list(criterionList,orderList,null,null,aliasMap);
        if(list.size() == 0){
            return null;
        }else {
            return list.get(0);
        }
    }

    @Override
    public List<CountGameDetailedCommand> count(Date date) {
        return phoneBetRepository.count(date);
    }


    private List<Criterion> criteria(ListPhoneBetCommand command){

        List<Criterion> criterionList = new ArrayList<>();

        if(!CoreStringUtils.isEmpty(command.getName())){
            User user = userManagerService.searchByName(command.getName());
            criterionList.add(Restrictions.eq("user",user != null ? user : "123"));
        }
        if(command.getStatus() != null && command.getStatus() != 0 ){
            criterionList.add(Restrictions.eq("status",command.getStatus()));
        }

        if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))){
            criterionList.add(Restrictions.ge("startDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.lt("endDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        return criterionList;
    }


}
