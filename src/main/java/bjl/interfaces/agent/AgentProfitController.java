package bjl.interfaces.agent;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.IAgentProfitAppService;
import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.core.timer.AgentProfitTimer;
import bjl.domain.model.agent.AgentProfit;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangjin on 2018/1/11.
 */
@Controller
@RequestMapping("agent_profit")
public class AgentProfitController {

    @Autowired
    private IGameDetailedRepository<GameDetailed,String> gameDetailedRepository;
    @Autowired
    private IAgentProfitAppService agentProfitAppService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
//        gameDetailedRepository.count();
        AgentProfitTimer profitTimer = new AgentProfitTimer();
        profitTimer.profit();
        return "OK";
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListAgentProfitCommand command){
        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if (sessionUser == null) {
            return new ModelAndView("redirect:/login_hf_889");
        }
        if("firstAgent".equals(sessionUser.getRoles().get(0))){
            command.setFirstId(sessionUser.getId());
            return new ModelAndView("/agentprofit/first","pagination",agentProfitAppService.pagination(command))
                    .addObject("command",command);
        }
        if("secondAgent".equals(sessionUser.getRoles().get(0))){
            command.setSecondId(sessionUser.getId());
            return new ModelAndView("/agentprofit/second","pagination",agentProfitAppService.pagination(command))
                    .addObject("command",command);
        }

        return new ModelAndView("/agentprofit/list","pagination",agentProfitAppService.pagination(command))
                .addObject("command",command);
    }
}
