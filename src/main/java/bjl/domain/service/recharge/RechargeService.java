package bjl.domain.service.recharge;

import bjl.application.account.IAccountAppService;
import bjl.application.recharge.command.CreateRechargeCommand;
import bjl.application.recharge.command.ListRechargeCommand;
import bjl.application.scoredetailed.IScoreDetailedAppService;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.application.systemconfig.ISystemConfigAppService;
import bjl.core.enums.YesOrNoStatus;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.pay.PayNotify;
import bjl.domain.model.recharge.IRechargeRepository;
import bjl.domain.model.recharge.Recharge;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.User;
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
 * Created by zhangjin on 2017/12/27.
 */
@Service("rechargeService")
public class RechargeService implements IRechargeService{

    @Autowired
    private IAccountAppService accountAppService;
    @Autowired
    private IRechargeRepository<Recharge, String> rechargeRepository;
    @Autowired
    private ISystemConfigAppService systemConfigAppService;
    @Autowired
    private IScoreDetailedAppService scoreDetailedAppService;

    @Override
    public List<Object[]> list(JSONObject jsonObject) {

        return rechargeRepository.list(jsonObject.getString("userid"));

    }

    /**
     * 创建订单
     * @return
     */
    @Override
    public Recharge createOrder(CreateRechargeCommand command) {

        Account account =  accountAppService.searchByUsername(command.getUserId());
        if(account == null){
            return null;
        }
        String orderNo;
        while (true) {
            orderNo = "vt"+ CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
            Recharge recharge = rechargeRepository.byOrderNo(orderNo);
            if (null == recharge) {
                break;
            }
        }

        Recharge recharge = new Recharge(account,orderNo,command.getMoney(), YesOrNoStatus.NO,command.getPayType(),command.getPass());
        rechargeRepository.save(recharge);
        return recharge;
    }

    /**
     * 检查订单
     * @param notify
     * @return
     */
    @Override
    public boolean payNotify(PayNotify notify) {
        //订单号同步处理
        synchronized (notify.getOrderNo()){
            //获取系统订单
            Recharge recharge = rechargeRepository.byOrderNo(notify.getOrderNo());
            if (recharge == null) {
                java.lang.System.out.println("没有查询到【" + notify.getOrderNo() + "】的订单");
                return false;
            }
            if(recharge.getIsSuccess() == YesOrNoStatus.NO && recharge.getMoney().compareTo(notify.getOrderMoney())==0 ){
                //获取充值比例
                SystemConfig systemConfig = systemConfigAppService.get();
                if(systemConfig.getRechargeRatio() == null){
                    systemConfig.setRechargeRatio(BigDecimal.valueOf(1));
                }
                //实际得到的游戏币
                BigDecimal money = recharge.getMoney().multiply(systemConfig.getRechargeRatio());
                //更新用户积分
                CreateScoreDetailedCommand scoreDetailedCommand = new CreateScoreDetailedCommand(recharge.getAccount().getUserName(),money,4);
                User user = scoreDetailedAppService.create(scoreDetailedCommand);
                if(user != null){
                    //更新充值记录
                    recharge.changeIsSuccess(YesOrNoStatus.YES);
                    recharge.setPayTime(new Date());
                    rechargeRepository.update(recharge);
                    return true;
                }else {
                    System.out.println("用户不存在");
                }
            } else {
                java.lang.System.out.println("状态:"+ (recharge.getIsSuccess() == YesOrNoStatus.NO));
                java.lang.System.out.println("金额:"+ (recharge.getMoney().compareTo(notify.getOrderMoney()) == 0));
                java.lang.System.out.println("订单【"+notify.getOrderNo()+"】验证失败...");
            }
        }
        return false;
    }


    @Override
    public Pagination<Recharge> pagination(ListRechargeCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getName())) {
            criterionList.add(Restrictions.like("account.name", command.getName(), MatchMode.ANYWHERE));
            aliasMap.put("account", "account");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (null != command.getIsSuccess() && command.getIsSuccess() != YesOrNoStatus.ALL) {
            criterionList.add(Restrictions.eq("isSuccess", command.getIsSuccess()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return rechargeRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,orderList,null);
    }

    @Override
    public Map<String, BigDecimal> sum(Date date) {

        return rechargeRepository.sum(date);
    }

}
