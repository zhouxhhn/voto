package bjl.domain.service.agent;

import bjl.application.agent.IAgentAppService;
import bjl.application.agent.command.ListAgentCommand;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.account.IAccountRepository;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.agent.IAgentRepository;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.ratio.Ratio;
import bjl.domain.model.ratiocheck.IRatioCheckRepository;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.domain.model.user.User;
import bjl.domain.service.ratio.IRatioService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 代理
 * Created by zhangjin on 2017/12/26.
 */
@Service("agentService")
public class AgentService implements IAgentService{

    @Autowired
    private IAgentRepository<Agent, String> agentRepository;
    @Autowired
    private IAccountRepository<Account, String> accountRepository;
    @Autowired
    private IAgentConfigService agentConfigService;
    @Autowired
    private IRatioCheckRepository<RatioCheck, String> ratioCheckRepository;
    @Autowired
    private IRatioService ratioService;
    @Autowired
    private IAgentAppService agentAppService;

    /**
     * 绑定代理
     * @param user
     * @param parent
     * @return
     */
    @Override
    public Agent create(Account user, Account parent) {
        Agent agent = new Agent();
        agent.setParent(parent);
        agent.setUser(user);
        agent.setCreateDate(new Date());
        agentRepository.save(agent);
        return agent;
    }

    @Override
    public Account getAgent(Account user) {

        List<Criterion> criterionList = new ArrayList<>();
//        Map<String, String> aliasMap = new HashMap<>();

        criterionList.add(Restrictions.eq("user",user));
//        aliasMap.put("account","account");
        List<Agent> agents = agentRepository.list(criterionList,null);
        if(agents.size()>0){

            return agents.get(0).getParent();
        }

        return null;
    }

    @Override
    public void delete(Account account) {

        List<Criterion> criterionList = new ArrayList<>();

        criterionList.add(Restrictions.eq("parent",account));

        List<Agent> list = agentRepository.list(criterionList,null);
        for(Agent agent : list){
            agentRepository.delete(agent.getParent().getId());
        }
    }

    @Override
    public List<Agent> agentList(Account parent) {

        List<Criterion> criterionList = new ArrayList<>();

        criterionList.add(Restrictions.eq("parent",parent));

        return agentRepository.list(criterionList,null);
    }

    /**
     * 下级人数
     * @param account
     * @return
     */
    @Override
    public Integer count(Account account) {
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("parent",account));
        List<Agent> list = agentRepository.list(criterionList,null);
        return list.size();
    }

    @Override
    public List<Agent> listAll() {
        return agentRepository.findAll();
    }

    @Override
    public Pagination<Agent> getLower(ListAgentCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        aliasMap.put("parent","parent");
        criterionList.add(Restrictions.eq("parent.id",command.getParentId()));

        if(!CoreStringUtils.isEmpty(command.getName())){
            criterionList.add(Restrictions.like("account.name",command.getName(),MatchMode.ANYWHERE));
            aliasMap.put("user","account");
        }
        if(command.getLevel() != null && command.getLevel() != 0){
            aliasMap.put("user","account");
            aliasMap.put("account.roles","role");

            if(command.getLevel() == 2){
                criterionList.add(Restrictions.eq("role.name","secondAgent"));
            } else if (command.getLevel() == 3) {
                criterionList.add(Restrictions.eq("role.name","user"));
            }
        }

        return agentRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,null,null);
    }

    @Override
    public String edit(String id, BigDecimal high,BigDecimal fact, BigDecimal R) {


        if(high == null || R == null || fact == null || high.compareTo(BigDecimal.valueOf(0)) <0 || fact.compareTo(BigDecimal.valueOf(0)) < 0 ||
                R.compareTo(BigDecimal.valueOf(0)) < 0){
            return "数据填写错误";
        }

        //查询上级代理配置
        Agent agent = agentRepository.getById(id);
        AgentConfig parentConfig = agentConfigService.getByAccount(agent.getParent());
        AgentConfig agentConfig = agentConfigService.getByAccount(agent.getUser());
        if(parentConfig == null || agentConfig == null){
            return "代理配置不存在或出错";
        }
        //必须满足 R1*H2-R2*H1 > 0
        if(parentConfig.getValueR().multiply(high).compareTo(R.multiply(parentConfig.getHighRatio())) <0){
            return "占比填写不符合";
        }


        if(high.compareTo(parentConfig.getHighRatio())>0){
            return "最高占比不能超过上级最高占比";
        }
        BigDecimal firstFact = parentConfig.getFactRatio() == null ? BigDecimal.valueOf(0) : parentConfig.getFactRatio();
        if(BigDecimal.valueOf(90).compareTo(firstFact.add(fact)) <0){
            return "实际占比不能超过90%";
        }

        if(R.compareTo(parentConfig.getValueR()) >0 ){
            return "R值不能大于上级R值";
        }

        //申请修改占比或R值
        RatioCheck ratioCheck = new RatioCheck();
        ratioCheck.setParent(agent.getParent());
        ratioCheck.setUser(agent.getUser());

        ratioCheck.setOldR(agentConfig.getValueR());
        ratioCheck.setOldHigh(agentConfig.getHighRatio());
        ratioCheck.setNewFirstRatio(agentConfig.getFactRatio());

        ratioCheck.setNewR(R);
        ratioCheck.setNewHigh(high);
        ratioCheck.setNewFirstRatio(fact);

        ratioCheck.setStatus(1);
        ratioCheck.setType(1);
        ratioCheck.setCreateDate(new Date());
        ratioCheckRepository.save(ratioCheck);

        return "申请修改成功";
    }

    /**
     * 修改对玩家的占比或R值
     * @param id
     * @param R
     * @return
     */
    @Override
    public String edit(String id,BigDecimal nFact, BigDecimal R) {

        Agent agent = agentRepository.getById(id);
        AgentConfig parentConfig = agentConfigService.getByAccount(agent.getParent());

        if(parentConfig == null){
            return "代理配置不存在";
        }

        if(R == null || nFact == null || nFact.compareTo(BigDecimal.valueOf(0)) <0 || R.compareTo(BigDecimal.valueOf(0)) <0){
            return "配置错误";
        }

        if(R.compareTo(parentConfig.getValueR()) >0){
            return "配置超范围";
        }


        Ratio ratio = ratioService.getByAccount(agent.getUser().getId());

        if(ratio.getCompanyFact()!= null){
            if(BigDecimal.valueOf(100).compareTo(nFact.add(ratio.getCompanyFact())) <0){
                return "占比超范围";
            }
        }else {
            if(BigDecimal.valueOf(90).compareTo(nFact) <0){
                return "占比超范围";
            }
        }


        //申请修改R值
        RatioCheck ratioCheck = new RatioCheck();

        ratioCheck.setParent(agent.getParent());
        ratioCheck.setPlay(agent.getUser());

        ratioCheck.setOldR(agent.getUser().getR());
        ratioCheck.setNewFirstRatio(ratio.getFirstFact());

        ratioCheck.setNewR(R);
        ratioCheck.setNewFirstRatio(nFact);

        ratioCheck.setStatus(1);
        ratioCheck.setType(2);
        ratioCheck.setCreateDate(new Date());
        ratioCheckRepository.save(ratioCheck);

        return "申请修改占比成功";
    }

    @Override
    public String editSecond(String id, BigDecimal nFact, BigDecimal R) {

        Agent agent = agentRepository.getById(id);
        AgentConfig agentConfig = agentConfigService.getByAccount(agent.getParent());

        if(agentConfig == null){
            return "代理配置不存在";
        }

        if(R == null || nFact == null || nFact.compareTo(BigDecimal.valueOf(0)) <0 || R.compareTo(BigDecimal.valueOf(0)) <0){
            return "配置错误";
        }

        if(R.compareTo(agentConfig.getValueR()) >0){
            return "R值超范围";
        }

        Ratio ratio = ratioService.getByAccount(agent.getUser().getId());

        if(ratio.getCompanyFact() == null){
            if(BigDecimal.valueOf(90).compareTo(nFact.add(ratio.getFirstFact())) <0){
                return "占比超范围";
            }
        }else {
            if(BigDecimal.valueOf(100).compareTo(ratio.getCompanyFact().add(nFact).add(ratio.getFirstFact())) <0){
                return "占比超范围";
            }
        }

        //申请修改R值
        RatioCheck ratioCheck = new RatioCheck();

        ratioCheck.setParent(agentAppService.getAgent(agent.getParent()));
        ratioCheck.setUser(agent.getParent());
        ratioCheck.setPlay(agent.getUser());

        ratioCheck.setOldR(agent.getUser().getR());
        ratioCheck.setOldFirstRatio(ratio.getFirstFact() == null ? BigDecimal.valueOf(0) : ratio.getFirstFact());
        ratioCheck.setOldSecondRatio(ratio.getSecondFact() == null ? BigDecimal.valueOf(0) : ratio.getSecondFact());

        ratioCheck.setNewR(R);
        ratioCheck.setNewFirstRatio(ratioCheck.getOldFirstRatio());
        ratioCheck.setNewSecondRatio(nFact);

        ratioCheck.setStatus(1);
        ratioCheck.setType(3);
        ratioCheck.setCreateDate(new Date());
        ratioCheckRepository.save(ratioCheck);

        return "申请修改占比成功";
    }

    @Override
    public String editFirst(String id, BigDecimal firstRatio, BigDecimal secondRatio, BigDecimal R) {

        Agent agent = agentRepository.getById(id);
        //二级代理配置
        AgentConfig agentConfig = agentConfigService.getByAccount(agent.getParent());


        if(agentConfig == null){
            return "代理配置不存在";
        }

        if(R == null || firstRatio == null || secondRatio == null || firstRatio.compareTo(BigDecimal.valueOf(0)) <0 || secondRatio.compareTo(BigDecimal.valueOf(0)) <0 || R.compareTo(BigDecimal.valueOf(0)) <0){
            return "配置错误";
        }

        if(R.compareTo(agentConfig.getValueR()) >0){
            return "R值超范围";
        }


        Ratio ratio = ratioService.getByAccount(agent.getUser().getId());

        if(ratio.getCompanyFact() == null){
            if(BigDecimal.valueOf(90).compareTo(firstRatio.add(secondRatio)) <0){
                return "占比超范围";
            }
        }else {
            if(BigDecimal.valueOf(100).compareTo(ratio.getCompanyFact().add(secondRatio).add(firstRatio)) <0){
                return "占比超范围";
            }
        }
        //申请修改R值
        RatioCheck ratioCheck = new RatioCheck();

        ratioCheck.setParent(agentAppService.getAgent(agent.getParent()));
        ratioCheck.setUser(agent.getParent());
        ratioCheck.setPlay(agent.getUser());

        ratioCheck.setOldR(agent.getUser().getR());
        ratioCheck.setOldFirstRatio(ratio.getFirstFact() == null ? BigDecimal.valueOf(0) : ratio.getFirstFact());
        ratioCheck.setOldSecondRatio(ratio.getSecondFact() == null ? BigDecimal.valueOf(0) : ratio.getSecondFact());

        ratioCheck.setNewR(R);
        ratioCheck.setNewFirstRatio(firstRatio);
        ratioCheck.setNewSecondRatio(secondRatio);

        ratioCheck.setStatus(1);
        ratioCheck.setType(4);
        ratioCheck.setCreateDate(new Date());
        ratioCheckRepository.save(ratioCheck);

        return "申请修改占比成功";
    }

}
