package bjl.interfaces.phonebet;

import bjl.application.phonebet.IPhoneBetAppService;
import bjl.application.phonebet.command.ListPhoneBetCommand;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/9.
 */
@Controller
@RequestMapping("/phone_bet")
public class PhoneBetController extends BaseController {

    @Autowired
    private IPhoneBetAppService phoneBetAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListPhoneBetCommand command){

        return new ModelAndView("/phonebet/phoneBetting","pagination",phoneBetAppService.pagination(command))
                .addObject("command",command).addObject("total",phoneBetAppService.total(command));
    }

    @RequestMapping(value = "/start")
    public ModelAndView start(String id,String jobNum){

        phoneBetAppService.jobStart(id,jobNum);

        return new ModelAndView("redirect:/phone_bet/pagination");
    }

    @RequestMapping(value = "/end")
    public ModelAndView end(String id, BigDecimal score,BigDecimal lose,String jobNum){

        phoneBetAppService.jobEnd(id,score,lose);

        return new ModelAndView("redirect:/phone_bet/pagination");
    }
}
