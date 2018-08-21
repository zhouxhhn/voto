package bjl.interfaces.profit;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.profitrecord.IProfitRecordAppService;
import bjl.core.enums.LoggerType;
import bjl.core.timer.AgentProfitTimer;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreHttpUtils;
import bjl.domain.model.profitrecord.ProfitRecord;
import bjl.interfaces.shared.web.BaseController;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhangjin on 2018/1/30
 */
@Controller
@RequestMapping("/profit")
public class ProfitController extends BaseController{

    @Autowired
    private IProfitRecordAppService profitRecordAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public ModelAndView profit(){

        ProfitRecord profitRecord = profitRecordAppService.getByTime();

        return new ModelAndView("/profit/edit","date",profitRecord!= null ? profitRecord.getCreateDate():"")
                .addObject("success",2);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit( HttpServletRequest request) {

        try {
            //统计收益
            new AgentProfitTimer().profit();
            //重置鞋局
            for(int i=0;i<3;i++){
                GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(i);
                if(gameStatus != null){
                    gameStatus.setxNum(1);
                    gameStatus.setjNum(1);
                } else {
                    gameStatus = new GameStatus();
                    gameStatus.setjNum(1);
                    gameStatus.setxNum(1);
                    gameStatus.setStatus(-1);
                    GlobalVariable.getGameStatusMap().put(i,gameStatus);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/error/500");
        }
        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                    "统计收益成功", CoreHttpUtils.getClientIP(request));
            loggerAppService.create(loggerCommand);
        }
        String date = CoreDateUtils.formatDateTime(new Date());
        return new ModelAndView("/profit/edit", "date", date).addObject("success",1);
    }

}
