package bjl.interfaces;

import bjl.application.account.representation.AccountRepresentation;
import bjl.core.Imagecaptcha.VerifyCodeUtils;
import bjl.core.common.Constants;
import bjl.core.redis.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * Created by pengyi on 2016/4/13.
 */
@Controller
public class IndexController {

    private final RedisService redisService;

    @Autowired
    public IndexController(RedisService redisService) {
        this.redisService = redisService;
    }

    @RequestMapping("/hf_889")
    public ModelAndView index(HttpSession session) {
        if (null != session.getAttribute(Constants.SESSION_USER)) {
            return new ModelAndView("redirect:/main");
        }
        return new ModelAndView("redirect:/login_hf_889");
    }

    @RequestMapping("/main")
    public ModelAndView main(HttpSession session) {
        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
        if(sessionUser != null && "admin".equals(sessionUser.getRoles().get(0)) ){
            return new ModelAndView("/main/index");

        }
//        if (null != session.getAttribute(Constants.SESSION_USER) ) {
//            return new ModelAndView("/main/index");
//        }
        return new ModelAndView("redirect:/login_hf_889");
    }

    @RequestMapping(value = "/agent")
    public ModelAndView agent(HttpSession session){
        if (null != session.getAttribute(Constants.SESSION_USER)) {
            return new ModelAndView("/main/agentIndex");
        }
        return new ModelAndView("redirect:/login_hf_889");
    }

    @RequestMapping(value = "/logged")
    public ModelAndView logged(HttpSession session) throws Exception {

        AccountRepresentation user = (AccountRepresentation) session.getAttribute(Constants.SESSION_USER);
        if (null != user) {
            Subject subject = SecurityUtils.getSubject();
            if (subject.hasRole("admin")) {
                return new ModelAndView("/index", "user", user)
                        .addObject("maintenance", redisService.exists("maintenance"));
            } else {
                return new ModelAndView("redirect:/logout");
            }
        }

        return new ModelAndView("redirect:/login_hf_889");
    }

    @RequestMapping("/maintenance")
    public ModelAndView maintenance() {
        if (redisService.exists("maintenance")) {
            redisService.delete("maintenance");
        } else {
            redisService.addCache("maintenance", "true");
        }
        return new ModelAndView("redirect:/logged");
    }

    @RequestMapping(value = "/verificationCode")
    @ResponseBody
    public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
//        String[] num = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",};
        String[] num1 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",};
        Random random = new Random();
        int int1 = random.nextInt(11);
        int int2 = random.nextInt(11);
        int f = new Random().nextInt(3);
        String[] fs = new String[]{"+", "-", "x"};

        switch (f) {
            case 0:
                session.setAttribute("code", int1 + int2);
                break;
            case 1:
                session.setAttribute("code", int1 - int2);
                break;
            case 2:
                session.setAttribute("code", int1 * int2);
                break;
//            case 3:
//                session.setAttribute("code", int1 / int2);
//                break;
        }

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        int w = 250, h = 50;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), num1[int1] + fs[f] + (num1[int2]));
    }

    @RequestMapping(value = "/page404")
    public ModelAndView page404() {
        return new ModelAndView("/error/404");
    }

    @RequestMapping(value = "/page500")
    public ModelAndView page500() {
        return new ModelAndView("/error/500");
    }

    @RequestMapping(value = "/test")
    public ModelAndView test() {
        return new ModelAndView("/test");
    }

    @RequestMapping(value = "/mahjong_test")
    public ModelAndView mahjong() {
        return new ModelAndView("/mahjong-test");
    }

    @RequestMapping(value = "/ie_update")
    public ModelAndView ipUpdate() {
        return new ModelAndView("/IeUpdate");
    }
}
