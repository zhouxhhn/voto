package bjl.interfaces.userManager.web;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.userManager.IUserManagerAppService;
import bjl.application.userManager.command.CreateUserCommand;
import bjl.application.userManager.command.ListUserCommand;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.application.userManager.command.RegisterUserCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.upDownPoint.IUpDownPointRepository;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.role.IRoleService;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.interfaces.shared.web.AlertMessage;
import bjl.interfaces.shared.web.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by dyp on 2017-12-15.
 */
@Controller
@RequestMapping("/user")
public class UserManagerController extends BaseController {
    @Autowired
    private ILoggerAppService loggerAppService;
    @Autowired
    private IUserManagerAppService userManagerAppService;

    @Autowired
    private  IUserRepository<User,String> userRepository;

    //显示玩家信息
    @RequestMapping(value = "/list")
    public ModelAndView pagination(ListUserCommand command) {

        return new ModelAndView("/user/PlayerManagement", "pagination", userManagerAppService.pagination(command))
                .addObject("sum",userManagerAppService.sum(command)).addObject("command",command);
    }

    //创建玩家
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ModelAndView create(CreateUserCommand command, Locale locale, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null){
            return new ModelAndView("login");
        }
        AlertMessage alertMessage;
        try {
            userManagerAppService.create(command);

            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "创建玩家", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);

        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        return new ModelAndView("redirect:/user/list","command",command);
    }

    //删除玩家
    @RequestMapping(value = "/delete" ,method = RequestMethod.GET)
    public ModelAndView delete( String id, Locale locale, HttpServletRequest request){
        User user = userRepository.getById(id);

        AlertMessage alertMessage;
        try {
            userManagerAppService.delete(user);
            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            if (sessionUser != null) {
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "删除玩家", CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }
        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("redirect:/user/list");
    }

    //置顶玩家
    @RequestMapping(value = "/setTop", method = RequestMethod.GET)
    public ModelAndView setTop(ModifyUserCommand command, Locale locale, HttpServletRequest request){
        AlertMessage alertMessage;

        User user;
        try {
            user = userManagerAppService.setTop(command.getId());

            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            if (sessionUser != null) {
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "置顶玩家"+ (user == null ? user.getAccount().getName() : ""), CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }
        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("redirect:/user/list");
    }

    //修改各人信息
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ModelAndView modify(ModifyUserCommand command, Locale locale, HttpServletRequest request){
        AlertMessage alertMessage;

        try{
          //更新
            userManagerAppService.update(command);
            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            if (sessionUser != null) {
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "修改个人信息", CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }
        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("redirect:/user/list");
    }


    //批量修改玩家信息
    @RequestMapping(value = "/listmodify",method = RequestMethod.POST)
    public ModelAndView listModify( ModifyUserCommand command,Locale locale, HttpServletRequest request){
        AlertMessage alertMessage;
        try {
            userManagerAppService.updateALL(command);
            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            if (sessionUser != null) {
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "批量修改玩家", CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }
        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("redirect:/user/list");
    }

    @RequestMapping(value = "/setVirtual", method = RequestMethod.GET)
    public ModelAndView setVirtual(String id, Locale locale, HttpServletRequest request){
        AlertMessage alertMessage;

        User user;
        try {
            user = userManagerAppService.setVirtual(id);

            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            if (sessionUser != null) {
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "虚拟玩家"+ (user == null ? user.getAccount().getName() : ""), CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }
        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("redirect:/user/list");

    }

    @RequestMapping(value = "/setPrintScreen", method = RequestMethod.GET)
    public ModelAndView setPrintScreen(String id, Locale locale, HttpServletRequest request){
        AlertMessage alertMessage;

        User user;
        try {
            user = userManagerAppService.setPrintScreen(id);
            if(user != null){
                AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
                if (sessionUser != null) {
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            (user.getPrintScreen()==1?"显示":"隐藏")+"玩家"+ (user == null ? user.getAccount().getName() : ""), CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }
            }
        }catch (Exception e){
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("redirect:/user/list");

    }

    @RequestMapping(value = "/changeScore")
    public ModelAndView changeScore(String id,BigDecimal score,Integer type,HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null || !"admin".equals(sessionUser.getRoles().get(0))){
            return new ModelAndView("redirect:/logged");
        }

        try {
            User user = userManagerAppService.changeScore(id,score,type);
            if(user != null){
                CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                        "给玩家["+user.getAccount().getUserName()+"]"+(type == 1 ? "上分":"下分")+"["+score+"]成功", CoreHttpUtils.getClientIP(request));
                loggerAppService.create(loggerCommand);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/user/list");
    }

}
