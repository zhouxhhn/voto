package bjl.interfaces;

import bjl.application.account.representation.AccountRepresentation;
import bjl.core.upload.FileUploadConfig;
import bjl.core.upload.IFileUploadService;
import bjl.core.upload.UploadResult;
import bjl.core.util.CoreHttpUtils;
import bjl.core.util.QRCodeUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pengyi on 2016/4/11.
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadResult upload(@RequestParam MultipartFile[] file) throws IOException {
        return fileUploadService.upload(file);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(String id) {
        return QRCodeUtils.createQRCode(id, fileUploadConfig);
    }

    @RequestMapping(value = "t")
    @ResponseBody
    public String t() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pwd", 123);
        jsonObject.put("acc", "z123456");
        String result = CoreHttpUtils.urlConnection("http://micro.win00853.com:8085/login", jsonObject.toJSONString());
        return result;
    }

    /**
     * 获取二维码
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "/my/code")
    @ResponseBody
    public String myCode(String userid) {
        return "http://www.ying906.com/" + fileUploadConfig.getQRCode() + userid + ".png";
    }

    @RequestMapping(value = "/code")
    public void code(HttpServletResponse response) {
        try {
            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            if (sessionUser == null) {
                response.sendRedirect("redirect:/login_hf_889");
                return;
            }
            String url = "http://www.ying906.com/" + fileUploadConfig.getQRCode() + sessionUser.getUserName() + ".png";

            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public void uploadHead(@RequestParam MultipartFile file, String userid, HttpServletResponse response) {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            response.addHeader("Access-Control-Max-Age", "1800");
            response.setContentType("text/plain; charset=utf-8");
            JSONObject jsonObject = fileUploadService.uploadHead(file, userid);
            response.getWriter().write(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 聊天图片
     *
     * @param file
     * @param userid 用户ID
     * @return
     */
    @RequestMapping(value = "/img/chat", method = RequestMethod.POST)
    public void uploadChat(@RequestParam MultipartFile file, String userid, Integer roomtype, HttpServletResponse response) {
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            response.addHeader("Access-Control-Max-Age", "1800");
            response.setContentType("text/plain; charset=utf-8");
            JSONObject jsonObject = fileUploadService.uploadChat(file, userid, roomtype);
            response.getWriter().write(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "vCode")
    public void vCode(HttpServletResponse response){
        response.setContentType("text/plain; charset=utf-8");
        try {
            JSONObject jsonObject = new JSONObject();
            response.getWriter().write(fileUploadService.validateCode(jsonObject).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public void deleteTemp(String roomNo) {
        fileUploadService.delete(roomNo);
    }

}
