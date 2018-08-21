package bjl.interfaces.upDownPoint.web;
import bjl.application.transfer.ITransferAppService;
import bjl.application.upDownPoint.IUpDownPointAppService;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.domain.model.upDownPoint.IUpDownPointRepository;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;


/**
 * Created by dyp on 2017-12-20.
 */
@Controller
@RequestMapping("/upDownPoint")
public class UpDownPointController extends BaseController{

@Autowired
private ITransferAppService transferAppService;

@Autowired
private IUpDownPointRepository upDownPointRepository;

@Autowired
private IUpDownPointAppService upDownPointAppService;


    @RequestMapping(value = "/list")
        public ModelAndView list(UpDownPointCommand command) {

        BigDecimal upPoint = new BigDecimal(0);
        BigDecimal downPoint = new BigDecimal(0);

        return new ModelAndView("/upDownPoint/list", "pagination", upDownPointAppService.pagination(command))
                .addObject("upPoint", upPoint).addObject("downPoint", downPoint)
                .addObject("command", command);
    }

}
