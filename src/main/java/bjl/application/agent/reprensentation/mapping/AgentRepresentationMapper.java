package bjl.application.agent.reprensentation.mapping;

import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.agent.reprensentation.AgentRepresentation;
import bjl.application.userManager.IUserManagerAppService;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.ratio.Ratio;
import bjl.domain.model.user.User;
import bjl.domain.service.ratio.IRatioService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/3/1
 */
@Component
public class AgentRepresentationMapper extends CustomMapper<Agent, AgentRepresentation> {

    @Autowired
    private IAgentConfigAppService configAppService;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IUserManagerAppService userManagerAppService;
    @Autowired
    private IRatioService ratioService;

    public void mapAtoB(Agent agent, AgentRepresentation representation, MappingContext context) {

        Account account = agent.getUser();
        representation.setParentId(account.getId());
        representation.setName(account.getName());
        representation.setUsername(account.getUserName());
        User user = userManagerAppService.searchByAccount(account);
        representation.setRestScore(user.getScore());

        if(account.getRoles().get(0).getName().equals("user")){

            representation.setLevel("3");
            representation.setR(account.getR());
            Ratio ratio = ratioService.getByAccount(account.getId());
            if(ratio != null){
                representation.setFirstRatio(ratio.getFirstFact());
                representation.setSecondRatio(ratio.getSecondFact());
            }

        } else if (account.getRoles().get(0).getName().equals("secondAgent")){
            representation.setLevel("2");
            //代理配置
            AgentConfig agentConfig = configAppService.getByAccount(account);
            if(agentConfig != null){
                representation.setHighRatio(agentConfig.getHighRatio());
                representation.setFirstRatio(agentConfig.getFactRatio());
                representation.setR(agentConfig.getValueR());
            }
            //下级人数
            representation.setSubCount(agentAppService.count(account));

        } else {
            representation.setLevel("其他");
        }

    }
}
