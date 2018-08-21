package bjl.interfaces.agent;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.IAgentAppService;
import bjl.application.agent.command.ListAgentCommand;
import bjl.application.agent.reprensentation.AgentRepresentation;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/11.
 */
@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private IAgentAppService agentAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListAgentCommand command){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }

        //如果是二级代理
        if("secondAgent".equals(sessionUser.getRoles().get(0))){
            return new ModelAndView("redirect:/agent/paginationSub");
        }

        command.setParentId(sessionUser.getId());

        Pagination<AgentRepresentation> pagination = agentAppService.getLower(command);

        return new ModelAndView("agent/first","pagination",pagination)
                .addObject("command",command);
    }

    @RequestMapping(value = "/paginationSub")
    public ModelAndView paginationSub(ListAgentCommand command){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        int role = 2;
        if("firstAgent".equals(sessionUser.getRoles().get(0))){
            role = 1;
        }else {
            command.setParentId(sessionUser.getId());
        }


        return new ModelAndView("agent/second","pagination",agentAppService.getLower(command))
                .addObject("command",command).addObject("role",role);
    }

    /**
     * 一级修改二级
     * @param id
     * @param high
     * @param fact
     * @param R
     * @return
     */
    @RequestMapping(value = "/edit/ratio",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String edit(String id, BigDecimal high ,BigDecimal fact, BigDecimal R){

        return agentAppService.edit(id,high,fact,R);

    }

    /**
     * 一级修改直接玩家
     * @param id
     * @param nFact
     * @param R
     * @return
     */
    @RequestMapping(value = "/edit/R",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String edit(String id,BigDecimal nFact, BigDecimal R){

        return agentAppService.edit(id,nFact,R);
    }

    /**
     * 二级修改玩家
     * @param id
     * @param nFact
     * @param R
     * @return
     */
    @RequestMapping(value = "/edit/second_ratio",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String secondRatio(String id,BigDecimal nFact, BigDecimal R){

        return agentAppService.editSecond(id,nFact,R);
    }

    /**
     * 一级修改玩家
     * @return
     */
    @RequestMapping(value = "/edit/first_ratio",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String firstRatio(String id,BigDecimal firstRatio,BigDecimal secondRatio, BigDecimal R){

        return agentAppService.editFirst(id,firstRatio,secondRatio,R);
    }

}
