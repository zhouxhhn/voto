package bjl.interfaces.bank;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.bank.IBankAppService;
import bjl.application.bank.command.ListBankCommand;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.core.enums.LoggerType;
import bjl.core.exception.NoFoundException;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.bank.Bank;
import bjl.interfaces.shared.web.AlertMessage;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by zhangjin on 2018/1/9.
 */
@Controller
@RequestMapping("/bank")
public class BankController extends BaseController {

    @Autowired
    private ILoggerAppService loggerAppService;

    @Autowired
    private IBankAppService bankAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListBankCommand command){
        return new ModelAndView("bank/bankList","pagination",bankAppService.pagination(command)).addObject("command",command);
    }

    @RequestMapping(value = "/unbind")
    public ModelAndView unbind(String id, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){
        AlertMessage alertMessage;
        Bank bank;
        try {
            bank = bankAppService.unbound(id);
        }catch (NoFoundException e) {
            alertMessage = new AlertMessage(this.getMessage("default.parameter.error", new Object[]{id}, locale));
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/bank/pagination");
        } catch (Exception e) {
            e.printStackTrace();
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "给用户[" + bank.getAccount().getName() + "]解绑银行卡成功", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        alertMessage = new AlertMessage(this.getMessage("default.edit.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/bank/pagination");
    }
}
