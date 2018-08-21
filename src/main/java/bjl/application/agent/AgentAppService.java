package bjl.application.agent;

import bjl.application.agent.command.ListAgentCommand;
import bjl.application.agent.reprensentation.AgentRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.user.User;
import bjl.domain.service.agent.IAgentService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("agentAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AgentAppService implements IAgentAppService{

    @Autowired
    private IAgentService agentService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Agent create(Account user, Account parent) {
        return agentService.create(user,parent);
    }

    @Override
    public Account getAgent(Account user) {
        return agentService.getAgent(user);
    }

    @Override
    public void delete(Account account) {
        agentService.delete(account);
    }

    @Override
    public List<Agent> agentList(Account parent) {
        return agentService.agentList(parent);
    }

    @Override
    public Pagination<AgentRepresentation> getLower(ListAgentCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        Pagination<Agent> pagination = agentService.getLower(command);

        return new Pagination<>(mappingService.mapAsList(pagination.getData(),AgentRepresentation.class),
                pagination.getCount(),command.getPage(),command.getPageSize());
    }

    @Override
    public Integer count(Account account) {
        return agentService.count(account);
    }

    @Override
    public String edit(String id, BigDecimal high, BigDecimal fact, BigDecimal R) {
        return agentService.edit(id,high,fact,R);
    }

    @Override
    public String edit(String id,BigDecimal nFact, BigDecimal R) {
        return agentService.edit(id,nFact,R);
    }

    @Override
    public String editSecond(String id, BigDecimal nFact, BigDecimal R) {
        return agentService.editSecond(id,nFact,R);
    }

    @Override
    public String editFirst(String id, BigDecimal firstRatio, BigDecimal secondRatio, BigDecimal R) {
        return agentService.editFirst(id,firstRatio,secondRatio,R);
    }

    @Override
    public List<Agent> listAll() {
        return agentService.listAll();
    }
}
