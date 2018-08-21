package bjl.application.agent.reprensentation.mapping;

import bjl.application.agent.reprensentation.AgentConfigRepresentation;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.service.agent.IAgentService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Component
public class AgentConfigRepresentationMapper extends CustomMapper<AgentConfig,AgentConfigRepresentation> {

    @Autowired
    private IAgentService agentService;

    public void mapAtoB(AgentConfig agentConfig, AgentConfigRepresentation representation, MappingContext context) {

        representation.setName(agentConfig.getUser().getAccount().getName());
        representation.setAlias(agentConfig.getUser().getAgentAlias());
        representation.setSubCount(agentService.count(agentConfig.getUser().getAccount()));
        representation.setRestScore(agentConfig.getUser().getScore());
        if(agentConfig.getLevel() == 2){
            representation.setFirstFact(agentConfig.getFactRatio());
        }
    }
}
