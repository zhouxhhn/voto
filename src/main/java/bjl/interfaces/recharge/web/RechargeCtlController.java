package bjl.interfaces.recharge.web;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.recharge.IRechargeCtlAppService;
import bjl.application.recharge.command.EditRechargeCtlCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.recharge.RechargeCtl;
import bjl.interfaces.shared.web.AlertMessage;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by zhangjin on 2017/9/29.
 */
@Controller
@RequestMapping("/rechargeCtl")
public class RechargeCtlController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ILoggerAppService loggerAppService;
    @Autowired
    private IRechargeCtlAppService rechargeCtlAppService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@ModelAttribute("command")EditRechargeCtlCommand command, Locale locale){
        AlertMessage alertMessage;
        RechargeCtl rechargeCtl;
        try {
            rechargeCtl  = rechargeCtlAppService.getRechargeCtl();
        } catch (Exception e) {
            logger.error(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        return new ModelAndView("/recharge/edit", "rechargeCtl", rechargeCtl)
                .addObject("command", command);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@Valid @ModelAttribute("command") EditRechargeCtlCommand command, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/recharge/edit", "command", command);
        }
        AlertMessage alertMessage;
        RechargeCtl rechargeCtl;
        try {
            rechargeCtl = rechargeCtlAppService.edit(command);
        } catch (Exception e) {
            logger.error(e.getMessage());
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
        return new ModelAndView("/recharge/edit", "rechargeCtl", rechargeCtl);
    }
}
