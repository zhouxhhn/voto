package bjl.interfaces.spread;

import bjl.application.ip.IIpAppService;
import bjl.application.spreadprofit.ISpreadProfitAppService;
import bjl.application.userManager.IUserManagerAppService;
import bjl.core.upload.FileUploadConfig;
import bjl.domain.model.user.User;
import bjl.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangjin on 2018/5/3
 */
@Controller
@RequestMapping("/spread")
public class SpreadController extends BaseController{

    @Autowired
    private ISpreadProfitAppService spreadProfitAppService;
    @Autowired
    private IUserManagerAppService userManagerAppService;
    @Autowired
    private IIpAppService ipAppService;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    /**
     * 抓取推广关系
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{id}")
    public ModelAndView spread(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){

        try {
            User user = userManagerAppService.getById(id);
            if(user == null){
                response.setContentType("text/plain; charset=utf-8");
                response.getWriter().write("不是有效链接，请勿更改链接");
                return null;
            }

            //获取IP
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            //保存对应关系
            ipAppService.create(id,ip);
            return new ModelAndView("/spread/download");
           // response.sendRedirect("https://www.pgyer.com/HCW9");
          //  new Date("")
//
//            if (new Date("2018-04-27 11:11").getTime() > 0) {
//                //android
//                response.sendRedirect("https://www.pgyer.com/HCW9");
//
//            } else {
//                //ios
//                response.sendRedirect("https://www.pgyer.com/A7jf");
//            }

//            return new ModelAndView("/spread/download", "spread", null);
//            int total = 1;
//            String count = request.getParameter("count");
//            if(count != null && !"".equals(count)&& "null".equals(count) ){
//                total =  Integer.parseInt(count)+1;
//                count = total+"";
//            }else{
//                count = total+"";
//            }
//            String userAgent = request.getHeader("user-agent").toLowerCase();
//            String url =  request.getRequestURL().toString()+"?count="+count;
//            int i = 0;
//            System.out.println("userAgent=" +userAgent);
//            boolean isAndroid = false;
//            while(total<10){
//                if (!userAgent.contains("iphone") && !userAgent.contains("ipad")) {
//                    isAndroid = true;
//                    break;
//                }
//                response.sendRedirect(url);
//            }
//            if(isAndroid){
//                response.sendRedirect("https://www.pgyer.com/HCW9");
//            }else{
//                response.sendRedirect("https://www.pgyer.com/A7jf");
//            }
//            System.out.println("userAgent ="+ userAgent);
//            if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
////                // 苹果下载
//                System.out.println("1111111=");
//                //response.sendRedirect(fileUploadConfig.getDomainName()+fileUploadConfig.getResourcePackage()+"ios.ipa");
////                System.out.println("222222");
//               response.sendRedirect("https://www.pgyer.com/A7jf");
//            } else {
////                // Android下载
//                System.out.println("333333=");
//                response.sendRedirect("https://www.pgyer.com/HCW9");
//              //  response.sendRedirect(fileUploadConfig.getDomainName()+fileUploadConfig.getResourcePackage()+"android.apk");
////                System.out.println("44444=");
//            }
            //跳转三方下载链接
            //response.sendRedirect(fileUploadConfig.getDomainName()+fileUploadConfig.getResourcePackage()+"v1.0_2018-06-29_signed_7zip_aligned.apk");

        }catch (Exception e){
            e.printStackTrace();
            response.setContentType("text/plain; charset=utf-8");
            try {
                response.getWriter().write("打开链接失败...");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
//
//    @RequestMapping(value = "/pagination")
//    public ModelAndView pagination(ListSpreadProfitCommand command){
//
//        return new ModelAndView("/spread/list","pagination",spreadProfitAppService.pagination(command))
//                .addObject("command",command);
//    }
//
//    @RequestMapping(value = "/report")
//    public ModelAndView report(String userId){
//
//        return new ModelAndView("/spread/report","list",spreadProfitAppService.report(userId));
//    }
//
//    @RequestMapping(value = "/detailed")
//    public ModelAndView detailed(String userId){
//
//        return new ModelAndView("/spread/detailed","detailed",spreadProfitAppService.profitDetailed(userId));
//    }

}
