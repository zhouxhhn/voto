package bjl.interfaces.ratiocheck;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.ratiocheck.IRatioCheckAppService;
import bjl.application.ratiocheck.command.ListRatioCheckCommand;
import bjl.core.enums.LoggerType;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.ratiocheck.RatioCheck;
import bjl.domain.model.transfer.Transfer;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangjin on 2018/3/1
 */
@Controller
@RequestMapping("/ratio_check")
public class RatioCheckController extends BaseController{

    @Autowired
    private IRatioCheckAppService ratioCheckAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListRatioCheckCommand command){

        return new ModelAndView("/ratiocheck/list","pagination",ratioCheckAppService.pagination(command))
                .addObject("command",command);
    }

    /**
     * 通过审核
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ModelAndView pass(String id, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        try {
            RatioCheck ratioCheck = ratioCheckAppService.pass(id);

            if(ratioCheck != null){
                if(ratioCheck.getType() == 1){
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "已通过对【"+ratioCheck.getParent().getName()+"】的实际占比修改申请",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                } else if (ratioCheck.getType() == 4){
//                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
//                            "已通过对【"+ratioCheck.getNormal().getName()+"】R值修改申请",
//                            CoreHttpUtils.getClientIP(request));
//                    loggerAppService.create(loggerCommand);
                } else {
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "已通过【" + ratioCheck.getParent().getName() + "】对【"+ratioCheck.getUser().getName()+"】占比修改申请",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
        return new ModelAndView("redirect:/ratio_check/pagination");
    }

    /**
     * 拒绝
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public ModelAndView refuse(String id, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }

        try {
            RatioCheck ratioCheck = ratioCheckAppService.refuse(id);
            if(ratioCheck != null){
                if(ratioCheck.getType() == 1){
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "已拒绝【"+ratioCheck.getParent().getName()+"】对【"+ratioCheck.getUser().getName()+"】的占比修改",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }else if (ratioCheck.getType() == 2){
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "已拒绝【"+ratioCheck.getParent().getName()+"】对【"+ratioCheck.getPlay().getName()+"】的占比修改",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }else if (ratioCheck.getType() == 3){
                    CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                            "已拒绝【" + ratioCheck.getUser().getName() + "】对【"+ratioCheck.getPlay().getName()+"】的占比修改",
                            CoreHttpUtils.getClientIP(request));
                    loggerAppService.create(loggerCommand);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }

        return new ModelAndView("redirect:/ratio_check/pagination");
    }
}
