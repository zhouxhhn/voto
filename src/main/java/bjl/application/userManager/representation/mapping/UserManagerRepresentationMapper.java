package bjl.application.userManager.representation.mapping;

import bjl.application.agent.IAgentAppService;
import bjl.application.userManager.representation.UserManagerRepresentation;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by dyp on 2018-1-12.
 */
@Component
public class UserManagerRepresentationMapper extends CustomMapper<User,UserManagerRepresentation> {

    @Autowired
    private IAgentAppService agentAppService;

    @Override
    public void mapAtoB(User user, UserManagerRepresentation representation, MappingContext context) {

            representation.setName(user.getAccount().getName());
            representation.setUsername(user.getAccount().getUserName());
            representation.setToken(user.getAccount().getToken());
            representation.setReferee(user.getAccount().getReferee() != null ? user.getAccount().getReferee().getName() : null );
            Account account = agentAppService.getAgent(user.getAccount());

            if(account != null){
                if("secondAgent".equals(account.getRoles().get(0).getName())){
                    representation.setSecondAgent(account.getName());
                    Account first = agentAppService.getAgent(account);
                    if(first != null){
                        representation.setFirstAgent(first.getName());
                    }
                }else {
                    representation.setFirstAgent(account.getName());
                }
            }

    }
}
