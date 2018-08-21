package bjl.interfaces.recharge.api;

import bjl.application.recharge.IRechargeAppService;
import bjl.application.recharge.command.CreateRechargeCommand;
import bjl.core.enums.PayType;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreHttpUtils;
import bjl.core.util.CoreStringUtils;
import bjl.core.util.MD5Util;
import bjl.domain.model.pay.JCNotify;
import bjl.domain.model.pay.PayNotify;
import bjl.domain.model.recharge.Recharge;
import bjl.interfaces.shared.api.BaseApiController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/1/23
 */
@Controller
@RequestMapping("/api/recharge")
public class ApiRechargeController extends BaseApiController {

    @Autowired
    private IRechargeAppService rechargeAppService;


    /**
     * 申请支付
     * @return
     */
    @RequestMapping(value = "/apply_recharge",method = RequestMethod.GET)
    public ModelAndView applyRecharge(CreateRechargeCommand command,HttpServletRequest request, HttpServletResponse response){



        try{
//            InputStream is = request.getInputStream();
//            StringBuffer req= new StringBuffer(2048);
//            int i;
//            byte[] buffer1 = new byte[2048];
//            i = is.read(buffer1);
//            for(int j = 0; j < i; j++){
//                req.append((char) buffer1[j]);
//            }
//            String str = req.toString();

//            command = new CreateRechargeCommand();
//            command.setClientIp("113.250.249.30");
//            command.setPass(1);
//            command.setPayType(PayType.QQPAY);
//            command.setMoney(BigDecimal.valueOf(10));
//            command.setUserId("24276787");

            System.out.println("接受到的支付参数："+JSONObject.toJSONString(command));
//            CreateRechargeCommand command = JSONObject.parseObject(str,CreateRechargeCommand.class);

            if(command != null && command.getMoney() != null){

                if(CoreStringUtils.isEmpty(command.getClientIp())){
                    command.setClientIp(CoreHttpUtils.getClientIP(request));
                }

                if (command.getMoney().compareTo(BigDecimal.valueOf(10)) >=0 ) {
                    command.setClientIp(CoreHttpUtils.getClientIP(request));
                    command.setPass(1);
                    return new ModelAndView("pay/pay_redirect_jc", "info", command);
                } else {
                    response.setContentType("text/plain; charset=utf-8");
                    response.getWriter().write("最低充值10");
                    return null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("/pay/fail");

    }

    @RequestMapping(value = "/success")
    public ModelAndView success() {

        return new ModelAndView("/pay/success");
    }

    /**
     * 拉取聚成支付
     * @param command
     * @param response
     */
    @RequestMapping(value = "/pullJC",method = RequestMethod.POST)
    public void pullJC(CreateRechargeCommand command, HttpServletResponse response){

        String message = "";
        try {
            if (command != null) {
                //创建系统订单
                Recharge recharge = rechargeAppService.createOrder(command);

                if(recharge == null){ //用户不存在
                    response.setContentType("text/plain; charset=utf-8");
                    response.getWriter().write("用户不存在");
                    return;
                }

                logger.info("用户【{}】,申请【{}】支付,支付金额【{}】成功",command.getUserId(),command.getPayType().getName(),command.getMoney());

                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("payKey", "457fb97ed77d4c12b2253f34c23e7bab");// 商户支付Key
                paramMap.put("orderPrice", recharge.getMoney().setScale(2).toString());
                paramMap.put("outTradeNo", recharge.getOrderNo());
                if(command.getPayType() == PayType.QQPAY){
                    paramMap.put("productType","70000203");
                } else if (command.getPayType() == PayType.BANK){
                    paramMap.put("productType","50000103");
                    paramMap.put("bankCode", "ICBC");//银行编码
                    paramMap.put("bankAccountType", "PRIVATE_DEBIT_ACCOUNT");//银行编码
                }
                paramMap.put("orderTime", CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
                paramMap.put("productName", command.getUserId());// 商品名称
                paramMap.put("orderIp", command.getClientIp());
                paramMap.put("returnUrl", "http://micro.173600.com:30020/api/recharge/success");
                paramMap.put("notifyUrl", "http://micro.173600.com:30020/api/recharge/notify_jc");

                //按参数名asscic码排序
                List<String> list = new ArrayList<>();
                list.addAll(paramMap.keySet());
                java.util.Collections.sort(list);
                StringBuilder strSign = new StringBuilder();
                for(String key : list){
                    strSign.append(key).append("=").append(paramMap.get(key)).append("&");
                }
                strSign.append("paySecret=").append("2ca7eae4563e41658833bf1846bfa3e3");

                paramMap.put("sign", MD5Util.MD5(strSign.toString()).toUpperCase());

                System.out.println("拉取聚成支付:"+paramMap);
                if(PayType.BANK == command.getPayType()){
                    JSONObject result = JSONObject.parseObject(CoreHttpUtils.post("http://106.15.82.115/b2cPay/b2cPay", paramMap));
                    System.out.println(result);
                    if("0000".equals(result.getString("returnCode"))){
                        response.sendRedirect(result.getString("url"));
                        return;
                    }else {
                        message = result.getString("returnMsg");
                    }
                } else if (PayType.QQPAY == command.getPayType()){
                    JSONObject result = JSONObject.parseObject(CoreHttpUtils.post("http://106.15.82.115/cnpPay/initPay", paramMap));
                    System.out.println(result);
                    if("0000".equals(result.getString("resultCode"))){
                        response.sendRedirect(result.getString("payMessage"));
                        return;
                    }else {
                        message = result.getString("errMsg");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().write("向第三方支付拉取支付信息失败,"+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 聚成支付异步通知接口
     *
     * @return
     */
    @RequestMapping(value = "/notify_jc")
    public void notifyHappy(JCNotify notify, HttpServletResponse response) {

        System.out.println("进入聚成异步通知");
        try {

            System.out.println("返回的数据:" + JSONObject.toJSONString(notify));

            //先验证签名
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("payKey", notify.getPayKey());
            paramMap.put("productName", notify.getProductName());
            paramMap.put("outTradeNo", notify.getOutTradeNo());
            paramMap.put("orderPrice", notify.getOrderPrice());
            paramMap.put("productType", notify.getProductType());
            paramMap.put("tradeStatus", notify.getTradeStatus());
            paramMap.put("successTime", notify.getSuccessTime());
            paramMap.put("orderTime", notify.getOrderTime());
            paramMap.put("trxNo", notify.getTrxNo());

            //按参数名asscic码排序
            List<String> list = new ArrayList<>();
            list.addAll(paramMap.keySet());
            java.util.Collections.sort(list);
            StringBuilder strSign = new StringBuilder();
            for (String key : list) {
                strSign.append(key).append("=").append(paramMap.get(key)).append("&");
            }
            strSign.append("paySecret=").append("2ca7eae4563e41658833bf1846bfa3e3");
            //生成的签名
            String sign = MD5Util.MD5(strSign.toString()).toUpperCase();
            System.out.println("签名字符串:" + sign);
            //对比签名
            if (sign.equals(notify.getSign().toUpperCase())) {
                //判断状态 是否支付成功
                if ("SUCCESS".equals(notify.getTradeStatus())) {
                    PayNotify payNotify = new PayNotify();
                    payNotify.setOrderMoney(BigDecimal.valueOf(Integer.valueOf(notify.getOrderPrice().split("\\.")[0])));
                    payNotify.setOrderNo(notify.getOutTradeNo());
                    if (rechargeAppService.payNotify(payNotify)) {

                        logger.info("支付成功,支付用户【" + notify.getProductName() + "】订单号【" + notify.getOutTradeNo() + "】,订单金额【" + notify.getOrderPrice() + "】");
                    } else {

                        System.out.println("订单异常");
                    }
                } else {
                    logger.info("支付失败，支付用户【" + notify.getProductName() + "】,订单号【" + notify.getOutTradeNo() + "】,订单金额【" + notify.getOrderPrice() + "】");
                }
            } else {

                System.out.println("签名失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        try {
            response.getOutputStream().write("SUCCESS".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
