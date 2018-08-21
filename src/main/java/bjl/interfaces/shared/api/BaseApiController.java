package bjl.interfaces.shared.api;

import bjl.core.ueditor.Encoder;
import com.alibaba.fastjson.JSON;
import bjl.core.api.ApiResponse;
import bjl.core.exception.ApiAuthenticationException;
import bjl.core.util.RSAUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Created by pengyi on 2016/4/15.
 */
public abstract class BaseApiController {

    protected Logger logger = LoggerFactory.getLogger(BaseApiController.class);

    protected <T> T authenticationAndConvert(HttpServletRequest request, Class<T> clz) throws ApiAuthenticationException {

//        byte[] content = null;
//        try {
//            Key privateKey = RSAUtils.getPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOaUJYNw5r2E5ar5lOa5ooCvvJCpFEYE1vUu1gCVcy265cJYxIZ1GIitGsSxMMME1FyOOb6yUW3QE4JmhHjG1w713j5IuSDyYgmcNos+c2xIzUZW7EwZON2pP2OX83ssGQe5bcl4iVnmqT/uJfckJTNfEZuzpoec/Ypb1G9et289AgMBAAECgYEAkxqC0FewLcrih3DRSV23SehUIepsz7r4tNWbnCW8pLkvKg1d2/ZKn6/oewIcfN7Q6Pen6Xx0LN3qBHCJJVCeFGv3FyJZ4wqzs3fiosZTX6m8heooEujeWknTGL3YYY7rIlpcvhvBbYL4NDl5OFUmvWRX8ahFHwPMKuTbvqSPJRUCQQD+v+fh6JKI807HWIwudWWU4Yja2gZUtgbVFu75ebV8pZaLEDtnWPxHiUEYyz4kCr27Ya6+bmpbfK0/QPu0YyjnAkEA57XerhPkcIfKEtwbQhaKulYeow4lNo12FNldLm2HwGHhWo2USOvhcu6zHWYGycaAIJJb0NnmsjkLlu4PV3CuOwJAKivYlhQrFdK5StTEt/glLcU8I4aOH73WWbYnL1NPkOfUiQbR3qTjdnApP5J9offJOtjL1ahvoN99yofWYyE7JwJASSMAzJV+z34s7FMJT4zp8PLp7LG0UUnJcb9CSDtOVA0RIpH5siKyIKLzal4f2mSLYLyRupRs2uhinhs6QHFSrQJBAJtzxXG0m2rJ3ew0EXMCoeFoyCxRZ/VppUz7MxfUpj9KBYFuqzkzdg3YOfxygNyGDHQzO/WEAW12F4brdHXjrPQ=");
//            content = RSAUtils.decrypt(privateKey, request.getParameter("content"));
//            if (content != null) {
//                return JSON.parseObject(content, clz);
//            }
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApiAuthenticationException();
//        }
        try{
            String jsonStr = new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8");
            String str = URLDecoder.decode(jsonStr,"utf-8");
//            System.out.println(new Date()+" 接受到的字符串："+str);
            return JSON.parseObject(str,clz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected void returnData(HttpServletResponse response, ApiResponse apiResponse) {
        response.setCharacterEncoding("utf-8");
        try {
//            Key privateKey = RSAUtils.getPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOaUJYNw5r2E5ar5lOa5ooCvvJCpFEYE1vUu1gCVcy265cJYxIZ1GIitGsSxMMME1FyOOb6yUW3QE4JmhHjG1w713j5IuSDyYgmcNos+c2xIzUZW7EwZON2pP2OX83ssGQe5bcl4iVnmqT/uJfckJTNfEZuzpoec/Ypb1G9et289AgMBAAECgYEAkxqC0FewLcrih3DRSV23SehUIepsz7r4tNWbnCW8pLkvKg1d2/ZKn6/oewIcfN7Q6Pen6Xx0LN3qBHCJJVCeFGv3FyJZ4wqzs3fiosZTX6m8heooEujeWknTGL3YYY7rIlpcvhvBbYL4NDl5OFUmvWRX8ahFHwPMKuTbvqSPJRUCQQD+v+fh6JKI807HWIwudWWU4Yja2gZUtgbVFu75ebV8pZaLEDtnWPxHiUEYyz4kCr27Ya6+bmpbfK0/QPu0YyjnAkEA57XerhPkcIfKEtwbQhaKulYeow4lNo12FNldLm2HwGHhWo2USOvhcu6zHWYGycaAIJJb0NnmsjkLlu4PV3CuOwJAKivYlhQrFdK5StTEt/glLcU8I4aOH73WWbYnL1NPkOfUiQbR3qTjdnApP5J9offJOtjL1ahvoN99yofWYyE7JwJASSMAzJV+z34s7FMJT4zp8PLp7LG0UUnJcb9CSDtOVA0RIpH5siKyIKLzal4f2mSLYLyRupRs2uhinhs6QHFSrQJBAJtzxXG0m2rJ3ew0EXMCoeFoyCxRZ/VppUz7MxfUpj9KBYFuqzkzdg3YOfxygNyGDHQzO/WEAW12F4brdHXjrPQ=");
//            byte[] bytes = RSAUtils.encrypt(privateKey, JSON.toJSONString(apiResponse));
//            if (bytes != null) {
//                response.getOutputStream().write(bytes);
//            }
//            System.out.println(new Date()+" 返回的字符串："+JSON.toJSONString(apiResponse));
            response.getOutputStream().write(JSON.toJSONBytes(apiResponse));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
