package bjl.application.agent;

import bjl.application.agent.command.EditAgentConfigCommand;
import bjl.application.agent.command.ListAgentConfigCommand;
import bjl.application.agent.reprensentation.AgentConfigRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.role.Role;
import bjl.domain.service.agent.IAgentConfigService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("agentConfigAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AgentConfigAppService implements IAgentConfigAppService{

    @Autowired
    private IAgentConfigService agentConfigService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<AgentConfigRepresentation> pagination(ListAgentConfigCommand command) {

        command.verifyPage();
        command.setPageSize(18);
        Pagination<AgentConfig> list = agentConfigService.pagination(command);
        List<AgentConfigRepresentation> data = mappingService.mapAsList(list.getData(),AgentConfigRepresentation.class);

        return new Pagination<>(data,list.getCount(),command.getPage(),command.getPageSize());
    }

    @Override
    public Pagination<AgentConfigRepresentation> paginationS(ListAgentConfigCommand command) {

        command.verifyPage();
        command.setPageSize(18);

        Pagination<Agent> list = agentConfigService.paginationS(command);

        List<AgentConfigRepresentation> data = mappingService.mapAsList(list.getData(),AgentConfigRepresentation.class);

        return new Pagination<>(data,list.getCount(),command.getPage(),command.getPageSize());
    }

    @Override
    public void create(Account account, Role role) {
        agentConfigService.create(account,role);
    }

    @Override
    public AgentConfig edit(EditAgentConfigCommand command) {
        return agentConfigService.edit(command);
    }

    @Override
    public void delete(Account account) {

        agentConfigService.delete(account);
    }

    @Override
    public AgentConfig getByAccount(Account account) {
        return agentConfigService.getByAccount(account);
    }

    @Override
    public void update(AgentConfig agentConfig) {
        agentConfigService.update(agentConfig);
    }

    @Override
    public AgentConfig getByAccountId(String id) {
        return agentConfigService.getByAccountId(id);
    }

    @Override
    public int update(String id, BigDecimal factRatio) {
        return agentConfigService.update(id,factRatio);
    }
}
