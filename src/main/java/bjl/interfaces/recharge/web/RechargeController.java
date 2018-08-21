package bjl.interfaces.recharge.web;

import bjl.application.recharge.IRechargeAppService;
import bjl.application.recharge.command.ListRechargeCommand;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangjin on 2018/1/23
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController extends BaseController{

    @Autowired
    private IRechargeAppService rechargeAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListRechargeCommand command){

        return new ModelAndView("/recharge/list","pagination",rechargeAppService.pagination(command))
                .addObject("command",command);
    }
}
