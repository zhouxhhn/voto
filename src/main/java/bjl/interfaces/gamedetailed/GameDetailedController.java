package bjl.interfaces.gamedetailed;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.agent.command.ListAgentProfitCommand;
import bjl.application.gamedetailed.IGameDetailedAppService;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.constants.VotoContants;
import bjl.domain.model.agent.AgentProfit;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.interfaces.shared.web.BaseController;
import bjl.utils.CommonUtils;
import bjl.utils.GenerateExcelUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        return new ModelAndView("/gamedetailed/game_detailed","pagination",gameDetailedAppService.pagination(command,null))
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

        return new ModelAndView("/agent/detailed","pagination",gameDetailedAppService.pagination(command,null))
                .addObject("total",gameDetailedAppService.total(command)).addObject("command",command);
    }

    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response){
        try{

            String boots = request.getParameter("boots");
            String name = request.getParameter("name");
            String games = request.getParameter("games");
            String hallType = request.getParameter("hallType");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            ListGameDetailedCommand command = new ListGameDetailedCommand();
            if(boots !=null && !"".equals(boots)){
                command.setBoots(Integer.parseInt(boots));
            }
            if(name !=null && !"".equals(name)){
                command.setName(name);
            }
            if(hallType !=null && !"".equals(hallType)){
                if(CommonUtils.isInteger(hallType)){
                    command.setHallType(Integer.parseInt(hallType));
                }
            }
            if(startDate !=null && !"".equals(startDate)){
                command.setStartDate(startDate);
            }
            if(endDate !=null && !"".equals(endDate)){
                command.setEndDate(endDate);
            }
            if(games !=null && !"".equals(games)){
                if(CommonUtils.isInteger(games)){
                    command.setGames(Integer.parseInt(games));
                }
            }
            List<GameDetailedRepresentation> list = gameDetailedAppService.pagination(command, VotoContants.EXPORT_EXCEL).getData();
            TotalGameDetailedCommand totalGameDetailedCommand = gameDetailedAppService.total(command);
            String[] header = {"靴局数","玩家","大厅","闲","庄","闲对","庄对","和","庄闲盈亏","三宝盈亏","有效流水", "庄闲洗码","三宝洗码"};
            String fileName = "输赢明细";
            GenerateExcelUtils.exportExcel(response, fileName, header, list,totalGameDetailedCommand, VotoContants.GAME_DETAIL_EXCEL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

