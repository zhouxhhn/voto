package bjl.interfaces.update.api;

import bjl.application.update.IUpdateAppService;
import bjl.application.update.repensentation.ApiUpdateRepresentation;
import bjl.interfaces.shared.api.BaseApiController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前端验证资源包
 * Created by zhangjin on 2017/12/23.
 */
@Controller
@RequestMapping("/api/upload")
public class ApiUpdatePackageController extends BaseApiController{

    @Autowired
    private IUpdateAppService updateAppService;

    @RequestMapping(value = "/get")
    public void get(HttpServletResponse response){

        try {

            ApiUpdateRepresentation update = updateAppService.apiGet();

            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(update));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
