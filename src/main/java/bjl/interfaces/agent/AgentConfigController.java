package bjl.interfaces.agent;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.agent.command.EditAgentConfigCommand;
import bjl.application.agent.command.ListAgentConfigCommand;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.agent.AgentConfig;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Controller
@RequestMapping("/agent_config")
public class AgentConfigController {

    @Autowired
    private IAgentConfigAppService agentConfigAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListAgentConfigCommand command){
        return new ModelAndView("/agentconfig/list","pagination",agentConfigAppService.pagination(command))
                .addObject("command",command).addObject("url","/agent_config/pagination");
    }

    @RequestMapping(value = "/pagination/second")
    public ModelAndView paginationS(ListAgentConfigCommand command){
        return new ModelAndView("/agentconfig/list","pagination",agentConfigAppService.paginationS(command))
                .addObject("command",command).addObject("url","/agent_config/pagination/second");
    }

    @RequestMapping(value = "/pagination/first")
    public ModelAndView paginationF(ListAgentConfigCommand command){
        return new ModelAndView("/agentconfig/list","pagination",agentConfigAppService.paginationS(command))
                .addObject("command",command).addObject("url","/agent_config/pagination/first");
    }

//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public ModelAndView edit(EditAgentConfigCommand command, BindingResult bindingResult, HttpServletRequest request) {
//
//        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
//
//        if(sessionUser == null){
//            return new ModelAndView("redirect:/logged");
//        }
//        if (bindingResult.hasErrors()) {
//            return new ModelAndView("redirect:/agent_config/pagination", "command", command);
//        }
//        try {
//            agentConfigAppService.edit(command);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ModelAndView("/error/500");
//        }
//
//        CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
//                "修改System信息成功", CoreHttpUtils.getClientIP(request));
//        loggerAppService.create(loggerCommand);
//
//        return new ModelAndView("redirect:/agent_config/pagination");
//    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(EditAgentConfigCommand command, HttpServletRequest request) {

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        try {
            AgentConfig agentConfig = agentConfigAppService.edit(command);

            if(agentConfig == null){
                return "1";
            }else {
                if(sessionUser != null){
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "修改"+agentConfig.getLevel()+"级代理["+agentConfig.getUser().getAccount().getName()+"]成功" +
                                    ",最高占比["+agentConfig.getHighRatio()+"],R值["+agentConfig.getValueR()+"]",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }
                return "0";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    @RequestMapping(value = "/ratio", method = RequestMethod.GET)
    public ModelAndView ratio(){
        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        try {
            AgentConfig agentConfig = agentConfigAppService.getByAccountId(sessionUser.getId());

            return new ModelAndView("/agent/ratio","config",agentConfig);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
    }

    @RequestMapping(value = "/ratio", method = RequestMethod.POST)
    @ResponseBody
    public String ratio(String id, BigDecimal factRatio){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return "0";
        }
        try {
            int result = agentConfigAppService.update(id,factRatio);

            return ""+result;
        }catch (Exception e){
            e.printStackTrace();
            return "-1";
        }
    }

}
