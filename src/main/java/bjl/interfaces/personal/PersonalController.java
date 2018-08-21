package bjl.interfaces.personal;

import bjl.application.personal.IPersonalAppService;
import bjl.application.personal.command.ListPersonalCommand;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by zhangjin on 2018/1/15.
 */
@Controller
@RequestMapping("personal")
public class PersonalController extends BaseController{

    @Autowired
    private IPersonalAppService personalAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListPersonalCommand command){

        return new ModelAndView("/personal/list","pagination",personalAppService.pagination(command))
                .addObject("command",command);
    }
}
