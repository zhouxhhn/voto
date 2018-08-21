package bjl.interfaces.carousel;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.carousel.ICarouselAppService;
import bjl.application.logger.ILoggerAppService;
import bjl.application.logger.command.CreateLoggerCommand;
import bjl.application.notice.command.ListNoticeCommand;
import bjl.core.enums.LoggerType;
import bjl.core.upload.IFileUploadService;
import bjl.core.util.CoreHttpUtils;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangjin on 2018/6/21
 */
@Controller
@RequestMapping("/carousel")
public class CarouselController extends BaseController {

    @Autowired
    private ICarouselAppService carouselAppService;
    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListNoticeCommand command){

        return new ModelAndView("/carousel/list","pagination",carouselAppService.pagination(command)).addObject("command",command);
    }

    @RequestMapping(value ="create",method = RequestMethod.POST)
    public ModelAndView create(MultipartFile file, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        String imageUrl = null;
        if(file.getSize() > 0 ){
            imageUrl = fileUploadService.uploadNotice(file).getString("url");
        }
        carouselAppService.create(imageUrl);

        CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                "添加轮播图成功", CoreHttpUtils.getClientIP(request));
        loggerAppService.create(loggerCommand);

        return new ModelAndView("redirect:/carousel/pagination");

    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(String id){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }

        carouselAppService.delete(id);

        return new ModelAndView("redirect:/carousel/pagination");
    }

}
