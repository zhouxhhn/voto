package bjl.application.agent.reprensentation.mapping;

import bjl.application.agent.reprensentation.AgentConfigRepresentation;
import bjl.application.userManager.IUserManagerAppService;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.ratio.Ratio;
import bjl.domain.model.user.User;
import bjl.domain.service.agent.IAgentConfigService;
import bjl.domain.service.agent.IAgentService;
import bjl.domain.service.ratio.IRatioService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/3/1
 */
@Component
public class AgentSRepresentationMapper extends CustomMapper<Agent, AgentConfigRepresentation> {

    @Autowired
    private IUserManagerAppService userManagerAppService;
    @Autowired
    private IAgentConfigService agentConfigService;
    @Autowired
    private IAgentService agentService;
    @Autowired
    private IRatioService ratioService;

    public void mapAtoB(Agent agent, AgentConfigRepresentation representation, MappingContext context) {

        if("user".equals(agent.getUser().getRoles().get(0).getName())){
            representation.setName(agent.getUser().getName());
            representation.setId(agent.getUser().getId());
            representation.setLevel("3");
            representation.setValueR(agent.getUser().getR());
            representation.setFactRatio(agent.getUser().getRatio());
            User user = userManagerAppService.searchByAccount(agent.getUser());
            representation.setRestScore(user != null ? user.getScore() :BigDecimal.valueOf(0));
            Ratio ratio = ratioService.getByAccount(agent.getUser().getId());
            if(ratio != null){
                representation.setFirstFact(ratio.getFirstFact());
                representation.setSecondFact(ratio.getSecondFact());
                representation.setCompanyFact(ratio.getCompanyFact());
            }

        }else {
            AgentConfig agentConfig = agentConfigService.getByAccount(agent.getUser());

            representation.setName(agent.getUser().getName());
            representation.setId(agentConfig.getId());
            representation.setLevel("2");
            representation.setValueR(agentConfig.getValueR());
            representation.setHighRatio(agentConfig.getHighRatio());
            representation.setFirstFact(agentConfig.getFactRatio());
            User user = userManagerAppService.searchByAccount(agent.getUser());
            representation.setRestScore(user != null ? user.getScore() :BigDecimal.valueOf(0));
            //下级人数
            representation.setSubCount(agentService.count(agent.getUser()));
        }

    }
}
