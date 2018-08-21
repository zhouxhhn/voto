package bjl.interfaces.update.web;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.update.IUpdateAppService;
import bjl.application.update.command.EditUpdateCommand;
import bjl.domain.model.update.Update;
import bjl.interfaces.shared.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 更新安装包
 * Created by zhangjin on 2017/12/22.
 */
@Controller
@RequestMapping("/update_package")
public class UpdatePackageController extends BaseController{

    @Autowired
    private IUpdateAppService updateAppService;

    @RequestMapping(value = "/get")
    public ModelAndView get(){
        Update update = updateAppService.get();
        return new ModelAndView("update/update_package","update",update);
    }

    @RequestMapping(value = "/update")
    public ModelAndView update(@RequestParam MultipartFile file, EditUpdateCommand command){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser == null){
            return new ModelAndView("redirect:logout");
        }
        command.setModifier(sessionUser.getName());
        boolean flag = updateAppService.updateFile(file,command);

        return new ModelAndView("update/update_package","update",updateAppService.get())
                .addObject("flag",flag);
    }

}
