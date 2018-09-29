package bjl.interfaces.guide;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.guide.IGuideAppService;
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
@RequestMapping("/guide")
public class GuideController extends BaseController {

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IGuideAppService guideAppService;
    @Autowired
    private ILoggerAppService loggerAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListNoticeCommand command){

        return new ModelAndView("/guide/list","pagination",guideAppService.pagination(command));
    }

    @RequestMapping(value ="create",method = RequestMethod.POST)
    public ModelAndView create(MultipartFile file,String title, String content, HttpServletRequest request){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }
        String imageUrl = null;
        if(file.getSize() > 0 ){
            imageUrl = fileUploadService.uploadNotice(file).getString("url");
        }
        guideAppService.create(title,content,imageUrl);

        CreateLoggerCommand loggerCommand = new CreateLoggerCommand(sessionUser.getId(), LoggerType.OPERATION,
                "创建新手指南成功", CoreHttpUtils.getClientIP(request));
        loggerAppService.create(loggerCommand);

        return new ModelAndView("redirect:/guide/pagination");

    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(String id){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null){
            return new ModelAndView("redirect:/logged");
        }

        guideAppService.delete(id);

        return new ModelAndView("redirect:/guide/pagination");
    }

}
