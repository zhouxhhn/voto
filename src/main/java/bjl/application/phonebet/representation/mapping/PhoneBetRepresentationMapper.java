package bjl.application.phonebet.representation.mapping;

import bjl.application.agent.IAgentAppService;
import bjl.application.phonebet.representation.PhoneBetRepresentation;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.phonebet.PhoneBet;
import bjl.domain.service.agent.IAgentConfigService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/4/9
 */
@Component
public class PhoneBetRepresentationMapper extends CustomMapper<PhoneBet,PhoneBetRepresentation> {

    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IAgentConfigService agentConfigService;

    public void mapAtoB(PhoneBet phoneBet, PhoneBetRepresentation representation, MappingContext context) {

        representation.setUser(phoneBet.getUser().getAccount().getName());
        if("user".equals(phoneBet.getUser().getAccount().getRoles().get(0).getName())){
            Account parent = agentAppService.getAgent(phoneBet.getUser().getAccount());
            if(parent != null){
                if("secondAgent".equals(parent.getRoles().get(0).getName())){
                    //上级为二级代理
                    representation.setSecondRatio(phoneBet.getUser().getAccount().getRatio());
                    AgentConfig parentConfig = agentConfigService.getByAccount(parent);
                    if(parentConfig != null){
                        representation.setFirstRatio(parentConfig.getFactRatio());
                    }
                }else {
                    //上级为一级代理
                    representation.setFirstRatio(phoneBet.getUser().getAccount().getRatio());
                }
            }
        }
    }
}
