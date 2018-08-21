package bjl.interfaces.robot;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.robot.IRobotAppService;
import bjl.application.robot.command.ListRobotCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.robot.Robot;
import bjl.interfaces.shared.web.AlertMessage;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by zhangjin on 2018/4/26
 */
@Controller
@RequestMapping("/robot")
public class RobotController extends BaseController{

    @Autowired
    private IRobotAppService robotAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping("/pagination")
    public ModelAndView pagination(ListRobotCommand command){

        return new ModelAndView("/robot/list","pagination",robotAppService.pagination(command))
                .addObject("command",command);
    }

    @RequestMapping(value = "/checkName",method = RequestMethod.POST)
    @ResponseBody
    public Integer checkName(String name){
        return robotAppService.checkName(name);
    }

    /**
     * 创建机器人
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ModelAndView create(Robot robot, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        AlertMessage alertMessage;

        try {
            robotAppService.create(robot);

            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "创建[" + robot.getHallType() + "]号厅机器人["+robot.getName()+"]成功", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);

            alertMessage = new AlertMessage(this.getMessage("default.create.success.messages", null, locale));
        }catch (Exception e){
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.create.failure.messages", null, locale));
        }
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/robot/pagination");
    }

    /**
     *删除机器人
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ModelAndView create(String id, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        AlertMessage alertMessage;

        try {
            Robot robot = robotAppService.delete(id);
            if(robot != null){
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "删除[" + robot.getHallType() + "]号厅机器人["+robot.getName()+"]成功", CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }
            alertMessage = new AlertMessage(this.getMessage("default.create.success.messages", null, locale));
        }catch (Exception e){
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.create.failure.messages", null, locale));
        }
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/robot/pagination");
    }
}
