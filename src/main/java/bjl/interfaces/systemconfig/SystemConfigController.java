package bjl.interfaces.systemconfig;

import bjl.application.account.IAccountAppService;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.spreadprofit.ISpreadProfitAppService;
import bjl.application.systemconfig.ISystemConfigAppService;
import bjl.application.systemconfig.command.EditSystemConfigCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.core.util.QRCodeUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.User;
import bjl.interfaces.shared.web.AlertMessage;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhangjin on 2018/1/17
 */
@Controller
@RequestMapping("system_config")
public class SystemConfigController extends BaseController{

    @Autowired
    private ISystemConfigAppService systemConfigAppService;
    @Autowired
    private ILoggerAppService loggerAppService;
    @Autowired
    private IAccountAppService accountAppService;
    @Autowired
    private ISpreadProfitAppService spreadProfitAppService;

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(){
        List<Account> list = accountAppService.listAll();
        for(Account account : list){

            if(spreadProfitAppService.getByAccount(account) == null){
                spreadProfitAppService.create(account);
            }
        }
        return "OK";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(Locale locale) {

        AlertMessage alertMessage;
        SystemConfig systemConfig;
        try {
            systemConfig = systemConfigAppService.get();
        } catch (Exception e) {
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/systemconfig/edit", "systemConfig", systemConfig);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("command") EditSystemConfigCommand command, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/system/edit", "command", command);
        }
        AlertMessage alertMessage;
        SystemConfig systemConfig;
        try {
            systemConfig = systemConfigAppService.edit(command);
        } catch (Exception e) {
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "修改System信息成功", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("/systemconfig/edit", "systemConfig", systemConfig);
    }

}
