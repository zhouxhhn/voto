package bjl.interfaces.withdraw;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.withdraw.IWithdrawAppService;
import bjl.application.withdraw.command.ListWithdrawCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.withdraw.Withdraw;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by zhangjin on 2018/1/24
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController {

    @Autowired
    private IWithdrawAppService withdrawAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListWithdrawCommand command){

        return new ModelAndView("/withdraw/list","pagination",withdrawAppService.pagination(command))
                .addObject("command",command);
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ModelAndView pass(String id, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        Withdraw withdraw;
        try {
            withdraw = withdrawAppService.pass(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "已通过【" + withdraw.getAccount().getName() + "】的提现申请", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        return new ModelAndView("redirect:/withdraw/pagination");
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public ModelAndView refuse(String id, HttpServletRequest request, RedirectAttributes redirectAttributes, Locale locale){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        Withdraw withdraw;
        try {
            withdraw = withdrawAppService.refuse(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "已拒绝【" + withdraw.getAccount().getName() + "】的提现申请", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        return new ModelAndView("redirect:/withdraw/pagination");
    }

}
