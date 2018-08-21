package bjl.interfaces.gamedetailed;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.gamedetailed.IGameDetailedAppService;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangjin on 2018/1/8.
 */
@Controller
@RequestMapping("/game_detailed")
public class GameDetailedController extends BaseController {

    @Autowired
    private IGameDetailedAppService gameDetailedAppService;

    /**
     * 分页条件查询
     * @param command
     * @return
     */
    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListGameDetailedCommand command){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/login_hf_889");
        }

        if(!sessionUser.getRoles().get(0).equals("admin")){
            command.setParentId(sessionUser.getId());
        }

        return new ModelAndView("/gamedetailed/game_detailed","pagination",gameDetailedAppService.pagination(command))
                .addObject("total",gameDetailedAppService.total(command)).addObject("command",command);
    }

    /**
     * 代理用户查询
     * @param command
     * @return
     */
    @RequestMapping(value = "/paginationAgent")
    public ModelAndView paginationAgent(ListGameDetailedCommand command){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/login_hf_889");
        }

            command.setParentId(sessionUser.getId());

        return new ModelAndView("/agent/detailed","pagination",gameDetailedAppService.pagination(command))
                .addObject("total",gameDetailedAppService.total(command)).addObject("command",command);
    }
}

