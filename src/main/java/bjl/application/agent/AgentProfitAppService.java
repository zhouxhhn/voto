package bjl.application.agent;

import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.constants.VotoContants;
import bjl.domain.model.agent.AgentProfit;
import bjl.domain.service.agent.IAgentProfitService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Pagination<AgentProfit> pagination(ListAgentProfitCommand command,String flag) {

        command.verifyPage();
        command.setPageSize(18);
        if(flag != null && VotoContants.EXPORT_EXCEL.equals(flag)){
            command.setPage(1);
            command.setPageSize(1000000);
        }
        Pagination<AgentProfit> pagination = agentProfitService.pagination(command);
        return pagination;
    }

    @Override
    public Pagination<AgentProfit>  exportList(ListAgentProfitCommand command, String flag) {
        int page = 1;
        if(command.getPage() != null){
            page = command.getPage();
        }

        command.setPage(1);
        if(flag != null && VotoContants.EXPORT_EXCEL.equals(flag)) {
            command.setPage(1);
        }
        command.setPageSize(Integer.MAX_VALUE);
        Pagination<AgentProfit> profitPagination = agentProfitService.exportList(command);
        Map<String,Integer> map = new HashMap<>();
        List<AgentProfit> agentProfits = new ArrayList<>();
        if(profitPagination != null && profitPagination.getData() != null && !profitPagination.getData().isEmpty()){
            List<AgentProfit> list = profitPagination.getData();
            for (int i = 0,size = list.size();i<size;i++){
                AgentProfit profit = list.get(i);
                if(map.get(profit.getPlay().getId()) == null){
                    map.put(profit.getPlay().getId(),i);
                    agentProfits.add(profit);
                }
            }
        }
        if(flag != null && VotoContants.EXPORT_EXCEL.equals(flag)) {
            profitPagination.setData(agentProfits);
        }else{
            if(agentProfits.size() >18){
                profitPagination.setCount(agentProfits.size());
                if(agentProfits.size() >page*18){
                    agentProfits = agentProfits.subList((page-1)*18,page*18);
                }else{
                    agentProfits = agentProfits.subList((page-1)*18,agentProfits.size());
                }
                profitPagination.setData(agentProfits);
                profitPagination.setPageSize(18);
                profitPagination.setPage(page);
            }
        }

        return profitPagination;
    }
}

