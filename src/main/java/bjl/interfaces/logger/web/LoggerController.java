package bjl.interfaces.logger.web;

import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.ListLoggerCommand;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangjin on 2017/6/9.
 */
@Controller
@RequestMapping("/logger")
public class LoggerController extends BaseController {

    private final ILoggerAppService loggerAppService;

    @Autowired
    public LoggerController(ILoggerAppService loggerAppService) {
        this.loggerAppService = loggerAppService;
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListLoggerCommand command) {

        return new ModelAndView("/operationLog/OperationLog", "pagination", loggerAppService.pagination(command))
                .addObject("command", command);
    }
}
