package bjl.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.auth.command.LoginCommand;
import bjl.core.common.CharsetConstant;
import bjl.core.common.Constants;
import bjl.core.exception.NoLoginException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 */
public class CoreHttpUtils {


    private static String[] HTTP_PROXY_HEADER_NAME = new String[]{
            "CLIENTIP",
            "X-FORWARDED-FOR"
    };

    private enum LoginPlatform {
        LINUX("PC", 0),
        WINDOW("PC", 1),
        IPHONE("iPhone", 2),
        IPAD("iPad", 3),
        MAC("Mac", 4),
        ANDROID("Android", 5);

        private String name;
        private int value;

        LoginPlatform(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    public static String getClientIP(HttpServletRequest request) {
        for (String headerName : HTTP_PROXY_HEADER_NAME) {
            String clientIP = request.getHeader(headerName);
            if (StringUtils.isNotBlank(clientIP)) {
                return clientIP;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 根据IP 获取地区
     *
     * @param ip IP
     * @return 地区
     */
    public static String getArea(String ip) {
        String area = CoreHttpUtils.urlConnection("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip, "");
        if (null != area && !area.isEmpty()) {
            area = new String(area.getBytes(), Charset.forName("GBK"));
            try {
                JSONObject response = JSON.parseObject(area, JSONObject.class);
                if (response.containsKey("city")) {
                    return response.getString("city");
                } else if (response.containsKey("province")) {
                    return response.getString("province");
                } else if (response.containsKey("country")) {
                    return response.getString("country");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "火星";
    }

    public static String getLoginPlatform(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        if (userAgent.contains(LoginPlatform.WINDOW.name())) {
            return LoginPlatform.WINDOW.getName();
        } else if (userAgent.contains(LoginPlatform.IPHONE.name())) {
            return LoginPlatform.IPHONE.getName();
        } else if (userAgent.contains(LoginPlatform.IPAD.name())) {
            return LoginPlatform.IPAD.getName();
        } else if (userAgent.contains(LoginPlatform.MAC.name())) {
            return LoginPlatform.MAC.getName();
        } else if (userAgent.contains(LoginPlatform.ANDROID.name())) {
            return LoginPlatform.ANDROID.getName();
        } else if (userAgent.contains(LoginPlatform.LINUX.name())) {
            return LoginPlatform.LINUX.getName();
        }
        return null;
    }

    /**
     * 保存用户cookie
     *
     * @param command
     * @param request
     * @param response
     */
    public static void writeCookie(LoginCommand command, HttpServletRequest request, HttpServletResponse response) {
        if (command.isRememberMe()) {
            String encryptStr = command.getUserName() + "," + command.getPassword();
            Cookie cookie = new Cookie(Constants.COOKIE_USER, CoreRc4Utils.encry_RC4_string(encryptStr, Constants.PASSWORD_ENCRYP_KEY));
            cookie.setMaxAge(604800);
            response.addCookie(cookie);
        } else {
            clearCookie(request, response);
        }
    }

    /**
     * 移除用户保存的cookie
     *
     * @param request
     * @param response
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_USER)) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public static AccountRepresentation getSessionAccount(Session session) {
        AccountRepresentation account = (AccountRepresentation) session.getAttribute(Constants.SESSION_USER);
        if (null == account) {
            throw new NoLoginException("没有登录");
        }
        return account;
    }

    public static String urlConnection(String url, String pa) {
        return urlConnection(url, pa, CharsetConstant.UTF8_STRING);
    }

    public static String urlConnection(String url, String pa, String charset) {

        String response = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type","application/json; charset=utf-8");


            // Send data
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
            // pa为请求的参数
            pw.print(pa);
            pw.flush();
            pw.close();

            // Get the response!
            int httpResponseCode = conn.getResponseCode();
            if (httpResponseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("HTTP response code: " + httpResponseCode +
                        "\nurl:" + url);
            }

            InputStream inputStream = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset));
            StringBuilder builder = new StringBuilder();
            String readLine;
            while (null != (readLine = br.readLine())) {
                builder.append(readLine);
            }
            inputStream.close();
            response = builder.toString();

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }


    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param params 提交参数
     * @return 提交响应
     */
    public static String post(String url, String params) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (null != params) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(params, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = client.execute(method);
            HttpEntity entity = result.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

    /**
     * 基于HttpClient 4.3的 Basic Authorization  POST方法
     *
     * @param url       提交的URL
     * @param params 提交参数
     * @return 提交响应
     */
    public static String postAuthorization(String url, String params) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(2000).build();//设置请求和传输超时时间

            HttpPost method = new HttpPost(url);
            //设置超时时间
            method.setConfig(requestConfig);
            String auth = "GTLCCNYJCWT_API" + ":" + "AG3GM3GM2";
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes("US-ASCII"));
            String authHeader = "Basic " + new String(encodedAuth);

            method.setHeader("Authorization",authHeader);
            method.setHeader("Content-Type","application/json");
            if (null != params) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(params, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = client.execute(method);
            HttpEntity entity = result.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

    /**
     * 基于HttpClient 4.3的通用GET方法
     *
     * @param url       提交的URL
     * @param params    提交参数
     * @return 提交响应
     */
    public static String get(String url, String params){
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        try {
            //创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
            String paramStr = params.replace(" ","");
            HttpGet httpRequest = new HttpGet(url+paramStr);
            System.out.println("支付请求实际参数:"+paramStr);
            //使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
            HttpResponse httpResponse =client.execute(httpRequest);
            if(httpResponse.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity httpEntity = httpResponse.getEntity();
                responseText = EntityUtils.toString(httpEntity,"UTF-8");//取出应答字符串
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseText;
    }

}
