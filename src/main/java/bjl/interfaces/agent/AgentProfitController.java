package bjl.interfaces.agent;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.IAgentProfitAppService;
import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.constants.VotoContants;
import bjl.core.timer.AgentProfitTimer;
import bjl.domain.model.agent.AgentProfit;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.utils.GenerateExcelUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            return new ModelAndView("/agentprofit/first","pagination",agentProfitAppService.pagination(command,null))
                    .addObject("command",command);
        }
        if("secondAgent".equals(sessionUser.getRoles().get(0))){
            command.setSecondId(sessionUser.getId());
            return new ModelAndView("/agentprofit/second","pagination",agentProfitAppService.pagination(command,null))
                    .addObject("command",command);
        }

        return new ModelAndView("/agentprofit/list","pagination",agentProfitAppService.pagination(command,null))
                .addObject("command",command);
    }

    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response){
        try{
            String playerName = request.getParameter("playerName");
            String firstName = request.getParameter("firstName");
            String secondName = request.getParameter("secondName");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            ListAgentProfitCommand command = new ListAgentProfitCommand();
            if(playerName !=null && !"".equals(playerName)){
                command.setPlayerName(playerName);
            }
            if(firstName !=null && !"".equals(firstName)){
                command.setFirstName(firstName);
            }
            if(startDate !=null && !"".equals(startDate)){
                command.setStartDate(startDate);
            }
            if(endDate !=null && !"".equals(endDate)){
                command.setEndDate(endDate);
            }
            if(secondName !=null && !"".equals(secondName)){
                command.setSecondName(secondName);
            }
            List<AgentProfit> list = agentProfitAppService.pagination(command, VotoContants.EXPORT_EXCEL).getData();
            String[] header = {"开工点","玩家名","一级代理人","二级代理人","转码数","上下数","一级最高","一级占比","二级最高","二级占比","公司占比",
                    "交收方式","三方上分","银行上分","一级手续费","二级手续贯","公司手续费","一级收益","二级收益","公司收益"
                    ,"一级R","二级R","玩家R","一级余额","二级余额","玩家余额"};
            String fileName = "代理收益";
            GenerateExcelUtils.exportExcel(response, fileName, header, list, null,VotoContants.AGENT_PROFIT_EXCEL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
