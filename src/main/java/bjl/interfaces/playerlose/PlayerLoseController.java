package bjl.interfaces.playerlose;

import bjl.application.playerlose.IPlayerLoseAppService;
import bjl.application.playerlose.command.ListPlayerLoseCommand;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Controller
@RequestMapping("player_lose")
public class PlayerLoseController extends BaseController{

    @Autowired
    private IPlayerLoseAppService playerLoseAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListPlayerLoseCommand command){

        return new ModelAndView("/playerlose/list","pagination",playerLoseAppService.pagination(command))
                .addObject("total",playerLoseAppService.total(command)).addObject("command",command);
    }

}
