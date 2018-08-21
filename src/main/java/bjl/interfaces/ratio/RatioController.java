package bjl.interfaces.ratio;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.ratio.IRatioAppService;
import bjl.application.ratio.command.EditRatioCommand;
import bjl.application.ratio.representation.RatioRepresentation;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.agent.Agent;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.ratio.Ratio;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/3/30
 */
@Controller
@RequestMapping("/ratio")
public class RatioController {

    @Autowired
    private IRatioAppService ratioAppService;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IAgentConfigAppService agentConfigAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/update")
    public void update(HttpServletResponse response){
        List<Agent> list = agentAppService.listAll();
        for(Agent agent : list){
            RatioRepresentation representation = ratioAppService.getByAccount(agent.getUser().getId());
            if(representation == null){
                if("user".equals(agent.getUser().getRoles().get(0).getName())){
                    if(agent.getParent() != null){

                        if("secondAgent".equals(agent.getParent().getRoles().get(0).getName())){
                            Ratio ratio = new Ratio();
                            ratio.setAccount(agent.getUser());
                            ratio.setCreateDate(new Date());
                            ratio.setSecondFact(agent.getUser().getRatio());
                            AgentConfig agentConfig = agentConfigAppService.getByAccount(agent.getParent());
                            if(agentConfig != null){
                                ratio.setFirstFact(agentConfig.getFactRatio());
                            }else {
                                ratio.setFirstFact(BigDecimal.valueOf(0));
                            }
                            ratio.setCompanyFact(BigDecimal.valueOf(100).subtract(ratio.getSecondFact()).subtract(ratio.getFirstFact()));

                            ratioAppService.update(ratio);

                        } else if("firstAgent".equals(agent.getParent().getRoles().get(0).getName())){
                            Ratio ratio = new Ratio();
                            ratio.setAccount(agent.getUser());
                            ratio.setCreateDate(new Date());
                            ratio.setSecondFact(BigDecimal.valueOf(0));
                            ratio.setFirstFact(agent.getUser().getRatio());
                            ratio.setCompanyFact(BigDecimal.valueOf(100).subtract(ratio.getFirstFact()));

                            ratioAppService.update(ratio);
                        }
                    }

                }
            }
        }
        try {
            response.getWriter().write("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户占比
     * @param id
     * @param response
     */
    @RequestMapping(value = "/info")
    private void info(String id,HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();
        try {

            RatioRepresentation ratio = ratioAppService.getByAccount(id);
            if(ratio!= null){
                jsonObject.put("data",ratio);
                jsonObject.put("status",0);
            }else {
                jsonObject.put("status",1);//用户配置不存在
            }
            response.getWriter().write(jsonObject.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("status",2);//获取配置出错
            try {
                response.getWriter().write(jsonObject.toJSONString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    /**
     * 修改占比
     * @param command
     * @param response
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public int edit(EditRatioCommand command, HttpServletRequest request, HttpServletResponse response){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        int result = 0;
        try {

            Ratio ratio = ratioAppService.edit(command);

            if(ratio!= null){

                if(sessionUser != null){
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "修改玩家["+ratio.getAccount().getName()+"]占比配置成功" +
                                    ",一级占比["+ratio.getFirstFact()+"],二级占比["+ratio.getSecondFact()+"],R值["+ratio.getAccount().getR()+"]",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }

            }else {
                result = 1;//用户配置不存在
            }

        }catch (Exception e){
            e.printStackTrace();
            result = -1;
        }
        return result;
    }
}
