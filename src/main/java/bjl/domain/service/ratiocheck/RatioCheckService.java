package bjl.domain.service.ratiocheck;

import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.ratiocheck.command.ListRatioCheckCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.account.IAccountRepository;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.ratio.Ratio;
import bjl.domain.model.ratiocheck.IRatioCheckRepository;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.domain.service.ratio.IRatioService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjin on 2018/3/1
 */
@Service("ratioCheckService")
public class RatioCheckService implements IRatioCheckService{

    @Autowired
    private IRatioCheckRepository<RatioCheck, String> ratioCheckRepository;
    @Autowired
    private IAgentConfigAppService agentConfigAppService;
    @Autowired
    private IAccountRepository<Account,String> accountRepository;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IRatioService ratioService;

    @Override
    public Pagination<RatioCheck> pagination(ListRatioCheckCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if (command.getStatus() != null && command.getStatus() != 0){
            criterionList.add(Restrictions.eq("status",command.getStatus()));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return ratioCheckRepository.pagination(command.getPage(),command.getPageSize(),criterionList,orderList);
    }

    /**
     * 允许修改
     * @param id
     * @return
     */
    @Override
    public RatioCheck pass(String id) {

        RatioCheck ratioCheck = ratioCheckRepository.getById(id);
        if (ratioCheck != null) {
            try {

                if (ratioCheck.getType() == 1) {
                    AgentConfig agentConfig = agentConfigAppService.getByAccount(ratioCheck.getUser());
                    AgentConfig parentConfig = agentConfigAppService.getByAccount(ratioCheck.getParent());

                    if (agentConfig != null) {
                        //修改一级对二级的占比
                        if(BigDecimal.valueOf(90).compareTo(ratioCheck.getNewFirstRatio()) <0){
                            ratioCheck.setStatus(5);
                            ratioCheckRepository.update(ratioCheck);
                            return ratioCheck;
                        }
                        // R1*H2-R2*H1 >0
                        if (parentConfig.getValueR().multiply(ratioCheck.getNewHigh())
                                .compareTo(ratioCheck.getNewR().multiply(parentConfig.getHighRatio())) < 0) {
                            //占比超范围
                            ratioCheck.setStatus(5);
                            ratioCheckRepository.update(ratioCheck);
                            return ratioCheck;
                        }
                        //更新状态
                        ratioCheck.setStatus(2);
                        ratioCheckRepository.update(ratioCheck);
                        //更新占比
                        agentConfig.setFactRatio(ratioCheck.getNewFirstRatio());
                        agentConfig.setValueR(ratioCheck.getNewR());
                        agentConfig.setHighRatio(ratioCheck.getNewHigh());
                        agentConfigAppService.update(agentConfig);

                        return ratioCheck;
                    }else {
                        //更新状态
                        ratioCheck.setStatus(4);
                        ratioCheckRepository.update(ratioCheck);
                    }
                    return ratioCheck;

                } else if (ratioCheck.getType() == 2) {

                    AgentConfig agentConfig = agentConfigAppService.getByAccount(ratioCheck.getParent());

                    if (agentConfig != null) {
                        //修改一级对二级的占比
                        if(BigDecimal.valueOf(90).compareTo(ratioCheck.getNewFirstRatio()) <0){
                            ratioCheck.setStatus(5);
                            ratioCheckRepository.update(ratioCheck);
                            return ratioCheck;
                        }

                        if (agentConfig.getValueR().compareTo(ratioCheck.getNewR()) < 0) {
                            //占比超范围
                            ratioCheck.setStatus(5);
                            ratioCheckRepository.update(ratioCheck);
                            return ratioCheck;
                        }
                        //更新状态
                        ratioCheck.setStatus(2);
                        ratioCheckRepository.update(ratioCheck);
                        //更新占比
                        Ratio ratio = ratioService.getByAccount(ratioCheck.getPlay().getId());
                        ratio.setFirstFact(ratioCheck.getNewFirstRatio());
                        ratioService.update(ratio);
                        //更新玩家R值
                        ratioCheck.getPlay().setR(ratioCheck.getNewR());
                        accountRepository.update(ratioCheck.getPlay());
                        return ratioCheck;
                    }else {
                        //更新状态
                        ratioCheck.setStatus(4);
                        ratioCheckRepository.update(ratioCheck);
                        return ratioCheck;
                    }
                } else if (ratioCheck.getType() == 3) {

                    //更新占比
                    Ratio ratio = ratioService.getByAccount(ratioCheck.getPlay().getId());
                    ratio.setSecondFact(ratioCheck.getNewSecondRatio());
                    //更新玩家R值
                    ratioCheck.getPlay().setR(ratioCheck.getNewR());
                    accountRepository.update(ratioCheck.getPlay());
                    //更新状态
                    ratioCheck.setStatus(2);
                    ratioCheckRepository.update(ratioCheck);
                    return ratioCheck;

                } else if (ratioCheck.getType() == 4) {
                    //更新占比
                    Ratio ratio = ratioService.getByAccount(ratioCheck.getPlay().getId());
                    ratio.setFirstFact(ratioCheck.getNewFirstRatio());
                    ratio.setSecondFact(ratioCheck.getNewSecondRatio());
                    //修改玩家R值
                    ratioCheck.getPlay().setR(ratioCheck.getNewR());
                    accountRepository.update(ratioCheck.getPlay());
                    //更新状态
                    ratioCheck.setStatus(2);
                    ratioCheckRepository.update(ratioCheck);
                    return ratioCheck;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ratioCheck.setStatus(5);
                ratioCheckRepository.update(ratioCheck);
                return ratioCheck;
            }
        }
        return null;
    }

    /**
     * 拒绝修改
     * @param id
     * @return
     */
    @Override
    public RatioCheck refuse(String id) {

        RatioCheck ratioCheck = ratioCheckRepository.getById(id);
        if(ratioCheck != null){
            //更新状态
            ratioCheck.setStatus(3);
            ratioCheckRepository.update(ratioCheck);
            return ratioCheck;
        }

        return null;
    }
}
