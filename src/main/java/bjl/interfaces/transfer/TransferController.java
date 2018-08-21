package bjl.interfaces.transfer;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.transfer.ITransferAppService;
import bjl.application.transfer.command.ListTransferCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.transfer.Transfer;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangjin on 2018/2/3
 */
@Controller
@RequestMapping("transfer")
public class TransferController extends BaseController{

    @Autowired
    private ITransferAppService transferAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListTransferCommand command){

        return new ModelAndView("/transfer/transfer","pagination",transferAppService.pagination(command))
                .addObject("command",command);
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ModelAndView pass(String id, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }

        Transfer transfer;
        try {
            transfer = transferAppService.pass(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
        if (transfer != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "已通过【" + transfer.getTransfer() + "】的转账申请", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        return new ModelAndView("redirect:/transfer/pagination");
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public ModelAndView refuse(String id, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        Transfer transfer;
        try {
            transfer = transferAppService.refuse(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "已拒绝【" + transfer.getTransfer() + "】的转账申请", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        return new ModelAndView("redirect:/transfer/pagination");
    }
}
