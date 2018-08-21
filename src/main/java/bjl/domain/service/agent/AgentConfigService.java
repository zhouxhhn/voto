package bjl.domain.service.agent;

import bjl.application.agent.IAgentAppService;
import bjl.application.agent.command.EditAgentConfigCommand;
import bjl.application.agent.command.ListAgentConfigCommand;
import bjl.application.systemconfig.ISystemConfigAppService;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.account.IAccountRepository;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.agent.IAgentConfigRepository;
import bjl.domain.model.agent.IAgentRepository;
import bjl.domain.model.ratiocheck.IRatioCheckRepository;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.domain.model.role.Role;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("agentConfigService")
public class AgentConfigService implements IAgentConfigService{

    @Autowired
    private IAgentConfigRepository<AgentConfig, String> agentConfigRepository;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IAgentRepository<Agent, String> agentRepository;
    @Autowired
    private IRatioCheckRepository<RatioCheck, String> ratioCheckRepository;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IAccountRepository<Account, String> accountRepository;
    @Autowired
    private IAgentConfigService agentConfigService;

    @Override
    public Pagination<AgentConfig> pagination(ListAgentConfigCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();

        if(!CoreStringUtils.isEmpty(command.getParentId())){

        }

        if(!CoreStringUtils.isEmpty(command.getAgentName())){
            User user = userManagerService.searchByName(command.getAgentName());
            criterionList.add(Restrictions.eq("user.id",user == null ? "123" : user.getId()));
            aliasMap.put("user","user");
        }
        if(command.getLevel() != null && command.getLevel() != 0){
            criterionList.add(Restrictions.eq("level",command.getLevel()));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return agentConfigRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,orderList,null);
    }

    @Override
    public Pagination<Agent> paginationS(ListAgentConfigCommand command) {

        AgentConfig account = agentConfigRepository.getById(command.getParentId());

        if(account  == null){
            return null;
        }
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();

        criterionList.add(Restrictions.eq("parent",account.getUser().getAccount()));

        if(!CoreStringUtils.isEmpty(command.getAgentName())){
            criterionList.add(Restrictions.like("account.name",command.getAgentName(),MatchMode.ANYWHERE));
            aliasMap.put("user","account");
        }
//        aliasMap.put("user","account");
//        aliasMap.put("account.roles","role");

        //criterionList.add(Restrictions.eq("role.name","user"));

        return agentRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,null,null);

    }


    @Override
    public void create(Account account, Role role) {

        User user = userManagerService.searchByUsername(account.getUserName());
        if(user != null){
            AgentConfig agentConfig = new AgentConfig();
            agentConfig.setUser(user);
            BigDecimal decimal = BigDecimal.valueOf(0);
            agentConfig.setValueR(decimal);
            agentConfig.setHighRatio(decimal);
            agentConfig.setRestScore(user.getScore());
            agentConfig.setFactRatio(decimal);
            switch (role.getName()) {
                case "firstAgent":
                    agentConfig.setLevel(1);
                    break;
                case "secondAgent":
                    agentConfig.setLevel(2);
                    break;
                default:
                    agentConfig.setLevel(0);
                    break;
            }
            agentConfig.setSubCount(0);
            agentConfig.setCreateDate(new Date());
            agentConfigRepository.save(agentConfig);
        }

    }

    /**
     * 管理员修改
     * @param command
     * @return
     */
    @Override
    public AgentConfig edit(EditAgentConfigCommand command) {

        AgentConfig agentConfig = agentConfigRepository.getById(command.getId());
        if(agentConfig != null){

            if(agentConfig.getLevel() == 1){
                //修改一级代理最高占比或R值
                if(command.getHighRatio().compareTo(BigDecimal.valueOf(90)) >0){
                    return null;
                }
                List<Agent> list = agentAppService.agentList(agentConfig.getUser().getAccount());
                for(Agent agent : list){
                    if("secondAgent".equals(agent.getUser().getRoles().get(0).getName())){

                        AgentConfig config = getByAccount(agent.getUser());
                        if(config == null){
                            return null;
                        }
                        if(command.getHighRatio().compareTo(config.getHighRatio()) <0 || command.getValueR().compareTo(config.getValueR())<0){
                            return null;
                        }
                    }else {
                        if(command.getValueR().compareTo(agent.getUser().getR() == null ? BigDecimal.valueOf(0) : agent.getUser().getR()) <0){
                            return null;
                        }
                    }
                }
                agentConfig.setHighRatio(command.getHighRatio());
                agentConfig.setValueR(command.getValueR());
                agentConfigRepository.update(agentConfig);

                return agentConfig;

            } else if(agentConfig.getLevel() == 2){


                //修改二级最高占比或R值
                //遍历二级的所有下级
                List<Agent> list = agentAppService.agentList(agentConfig.getUser().getAccount());
                //不能大于上级R值或最高占比
                AgentConfig parentConfig = agentConfigService.getByAccount(agentAppService.getAgent(agentConfig.getUser().getAccount()));
                if(parentConfig != null){
                    if(parentConfig.getValueR().compareTo(command.getValueR()) <0 || parentConfig.getHighRatio().compareTo(command.getHighRatio()) <0){
                        return null;
                    }
                }
                for (Agent agent : list) {
                    //且不能小于下级R值
                    if (command.getValueR().compareTo(agent.getUser().getR() == null ? BigDecimal.valueOf(0) : agent.getUser().getR()) < 0) {
                        return null;
                    }
                }
                agentConfig.setFactRatio(command.getFactRatio());
                agentConfig.setHighRatio(command.getHighRatio());
                agentConfig.setValueR(command.getValueR());
                agentConfigRepository.update(agentConfig);
                return agentConfig;
            }
        }
        return null;
    }

    @Override
    public void delete(Account account) {

        User user = userManagerService.searchByUsername(account.getUserName());

        if(user != null){
            List<Criterion> criterionList = new ArrayList<>();

            criterionList.add(Restrictions.eq("user",user));

            List<AgentConfig> list = agentConfigRepository.list(criterionList,null);

            if(list.size() >0){
                agentConfigRepository.delete(list.get(0).getUser().getId());
            }
        }
    }

    @Override
    public AgentConfig getByAccount(Account account) {

        if(account != null){
            List<Criterion> criterionList = new ArrayList<>();

            Map<String, String> aliasMap = new HashMap<>();
            aliasMap.put("user","user");
            criterionList.add(Restrictions.eq("user.account",account));

            List<AgentConfig> list = agentConfigRepository.list(criterionList,null,null,null,aliasMap);

            if(list.size() >0){
                return  list.get(0);
            }
        }
        return null;
    }

    @Override
    public void update(AgentConfig agentConfig) {
        if(agentConfig != null){
            agentConfigRepository.update(agentConfig);
        }
    }

    @Override
    public AgentConfig getByAccountId(String id) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        aliasMap.put("user","user");
        aliasMap.put("user.account","account");

        criterionList.add(Restrictions.eq("account.id",id));

        List<AgentConfig> list = agentConfigRepository.list(criterionList,null,null,null,aliasMap);
        if(list.size()> 0 ){
            return list.get(0);
        }

        return null;
    }

    @Override
    public int update(String id, BigDecimal factRatio) {

        AgentConfig agentConfig = agentConfigRepository.getById(id);
        if(agentConfig == null){
            return 1;
        }
        if(factRatio == null || factRatio.compareTo(BigDecimal.valueOf(0)) <0 ){
            return -2;
        }
        if(factRatio.compareTo(agentConfig.getHighRatio()) >0 ){
            return 2;
        }

        RatioCheck ratioCheck = new RatioCheck();
        if(agentConfig.getLevel() == 1){ //一级代理
            ratioCheck.setParent(agentConfig.getUser().getAccount());
//            ratioCheck.setSuperHighRatio(agentConfig.getHighRatio());
//            ratioCheck.setSuperFactRatio(factRatio);
//            ratioCheck.setSuperR(agentConfig.getValueR());
//            ratioCheck.setType(1);
        } else if (agentConfig.getLevel() == 2){
            //查询上级代理配置
            Agent agent = agentRepository.getById(id);
            AgentConfig parentConfig = getByAccount(agent.getParent());

            if((factRatio.add(parentConfig.getFactRatio())).compareTo(parentConfig.getHighRatio()) >0){
                return 4;
            }

            ratioCheck.setParent(parentConfig.getUser().getAccount());
//            ratioCheck.setSuperHighRatio(parentConfig.getHighRatio());
//            ratioCheck.setSuperFactRatio(parentConfig.getFactRatio());
//            ratioCheck.setSuperR(parentConfig.getValueR());
//
//            ratioCheck.setUser(agentConfig.getUser().getAccount());
//            ratioCheck.setLowerHighRatio(agentConfig.getHighRatio());
//            ratioCheck.setLowerFactRatio(agentConfig.getFactRatio());
//            ratioCheck.setLowerR(agentConfig.getValueR());
            ratioCheck.setType(2);
        }
        ratioCheck.setCreateDate(new Date());
        ratioCheck.setStatus(1);
        ratioCheckRepository.save(ratioCheck);
        return 3;
    }

}
