package bjl.application.agent;

import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.domain.model.agent.AgentProfit;
import bjl.domain.service.agent.IAgentProfitService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("agentProfitAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AgentProfitAppService implements IAgentProfitAppService{

    @Autowired
    private IAgentProfitService agentProfitService;

    @Override
    public void create(AgentProfit agentProfit) {
        agentProfitService.create(agentProfit);
    }

    @Override
    public Pagination<AgentProfit> pagination(ListAgentProfitCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return agentProfitService.pagination(command);
    }
}

