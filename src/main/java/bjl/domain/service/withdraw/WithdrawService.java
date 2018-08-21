package bjl.domain.service.withdraw;

import bjl.application.systemconfig.ISystemConfigAppService;
import bjl.application.withdraw.command.ListWithdrawCommand;
import bjl.core.enums.FlowType;
import bjl.core.exception.DataErrorException;
import bjl.core.exception.NoFoundException;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreHttpUtils;
import bjl.core.util.CoreStringUtils;
import bjl.core.util.MD5Util;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.model.withdraw.IWithdrawRepository;
import bjl.domain.model.withdraw.Withdraw;
import bjl.domain.service.bank.IBankService;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * Created by zhangjin on 2018/1/23
 */
@Service("withdrawService")
public class WithdrawService implements IWithdrawService{

    @Autowired
    private IWithdrawRepository<Withdraw, String> withdrawRepository;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IUserRepository<User, String> userRepository;
    @Autowired
    private IBankService bankService;
    @Autowired
    private ISystemConfigAppService systemConfigAppService;
    @Autowired
    private IScoreDetailedRepository<ScoreDetailed, String> scoreDetailedRepository;

    @Override
    public JSONObject applyWithdraw(JSONObject jsonObject) {

        User user = userManagerService.searchByUsername(jsonObject.getString("userid"));
        BigDecimal money = jsonObject.getBigDecimal("sum");
        BigDecimal charge = money.multiply(BigDecimal.valueOf(0.02)); //手续费
        if(user != null){
            //查询用户是否绑定银行卡
            Bank bank = bankService.info(jsonObject.getString("userid"));
            if(bank != null){
                SystemConfig systemConfig = systemConfigAppService.get();
                BigDecimal score = money.multiply(systemConfig.getRechargeRatio());
                if(user.getScore().compareTo(score) >= 0){
                    //先扣除用户积分
                    user.setScore(user.getScore().subtract(score));
                    user.setPrimeScore(user.getPrimeScore().subtract(score));
                    userRepository.update(user);
                    //再添加积分明细
                    ScoreDetailed scoreDetailed = new ScoreDetailed();
                    scoreDetailed.setUser(user);
                    scoreDetailed.setNewScore(user.getScore());
                    scoreDetailed.setScore(score.multiply(BigDecimal.valueOf(-1)));
                    scoreDetailed.setActionType(6);
                    scoreDetailedRepository.save(scoreDetailed);
                    //在添加提现记录
                    Withdraw withdraw = new Withdraw(user.getAccount(),money,charge,money.subtract(charge),1,bank.getBankName(),bank.getBankAccountNo(),"等待管理员审核",1);
                    withdrawRepository.save(withdraw);
                    jsonObject.put("code",0);
                    jsonObject.put("errmsg","申请提现成功");
                }else {
                    jsonObject.put("code",1);
                    jsonObject.put("errmsg","用户积分不足");
                }
            }else {
                jsonObject.put("code",1);
                jsonObject.put("errmsg","没有查询到用户银行卡信息");
            }
        }else {
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
        }



        return jsonObject;
    }

    @Override
    public Pagination<Withdraw> pagination(ListWithdrawCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();

        if(!CoreStringUtils.isEmpty(command.getName())){
            criterionList.add(Restrictions.like("account.name",command.getName(), MatchMode.ANYWHERE));
            aliasMap.put("account","account");
        }

        if(command.getStatus() != null && command.getStatus() != 0){
            criterionList.add(Restrictions.eq("status",command.getStatus()));
        }
        if(command.getSuccess() != null && command.getSuccess() !=0){
            criterionList.add(Restrictions.eq("success",command.getSuccess()));
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return withdrawRepository.pagination(command.getPage(),command.getPageSize(),criterionList,aliasMap,orderList,null);
    }

    /**
     * 拉起提现
     */
    private void pullWithdrawal(Withdraw withdraw) {

        try {
            Bank bank = bankService.info(withdraw.getAccount().getUserName());
            if (bank == null) {
                withdraw.setComment("用户没有绑定银行卡");
                throw new NoFoundException();
            }

            //获取提现手续费


            Map<String , String> paramMap = new HashMap<>();

            paramMap.put("payKey","457fb97ed77d4c12b2253f34c23e7bab"); // 商户支付Key
            paramMap.put("outTradeNo", java.lang.System.currentTimeMillis()+"");
            paramMap.put("orderPrice",withdraw.getActualMoney().setScale(2,RoundingMode.HALF_DOWN).toString());// 订单金额 , 单位:元
            paramMap.put("proxyType","T0");//交易类型 T1 T0
            paramMap.put("productType","B2CPAY");//产品类型 WEIXIN ALIPAY B2CPAY QUICKPAY QQ_PAY
            paramMap.put("bankAccountType","PRIVATE_DEBIT_ACCOUNT");//PUBLIC_ACCOUNT 对公  PRIVATE_DEBIT_ACCOUNT 对私借记卡
            paramMap.put("phoneNo","13713978205"); //代付银行手机号
            paramMap.put("receiverName",bank.getBankAccountName());//账户名 收款人账户名
            paramMap.put("certType","IDENTITY");//证件类型 IDENTITY 身份证
            paramMap.put("certNo","452323198009281658");//代付身份证号
            paramMap.put("receiverAccountNo",bank.getBankAccountNo());//银行账号
            paramMap.put("bankName",bank.getBankName());////开户行名称
            paramMap.put("bankCode","PINGANBANK");//联行号 什么都不用改，你请求时bankCode=PINGANBANK,就可以
            paramMap.put("bankClearNo","308584001024");//清算行行号
            paramMap.put("bankBranchNo","308584001024");//代付开户银行支行行号
            paramMap.put("bankBranchName","平安银行股份有限公司深圳深大支行");//代付开户行支行名称
            paramMap.put("province","广东省");//代付开户行省份
            paramMap.put("city","深圳市");//代付开户行城市

            /////签名及生成请求API的方法///
            SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry<String, Object> m : smap.entrySet()) {
                Object value = m.getValue();
                if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
                    stringBuffer.append(m.getKey()).append("=").append(m.getValue()).append("&");
                }
            }
            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

            String argPreSign = stringBuffer.append("&paySecret=").append("2ca7eae4563e41658833bf1846bfa3e3").toString();
            String signStr = MD5Util.MD5(argPreSign).toUpperCase();
            paramMap.put("sign",signStr);
            java.lang.System.out.println(paramMap);
            //请求三方代付
            JSONObject result = JSONObject.parseObject(CoreHttpUtils.post("http://106.15.82.115/accountProxyPay/initPay",paramMap));
            java.lang.System.out.println(CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS:")+"三方代付返回："+result);

            withdraw.setComment("请求三方代付成功");

//            if (result != null && "0000".equals(result.getString("resultCode")) && "REMIT_SUCCESS".equals(result.getString("remitStatus"))) {
//
//                //请求成功
//                withdrawa.setComment("请求三方代付成功");
//                withdrawa.setSuccess(1);
//                withdrawaRepository.update(withdrawa);
//                return;
//            }
//            assert result != null;
//            withdrawa.setComment(result.getString("errMsg"));
        } catch (Exception e) {
            withdraw.setComment("处理异常，请求三方代付失败");
            e.printStackTrace();
        }
        withdraw.setSuccess(1);
        withdrawRepository.update(withdraw);
    }

    @Override
    public Withdraw pass(String id) {

        Withdraw withdraw = withdrawRepository.getById(id);
        if(withdraw == null){
            throw new NoFoundException();
        }
        if(withdraw.getStatus() != 1){
            throw new DataErrorException();
        }
        //审核通过
        withdraw.setStatus(2);
        //更新提现记录
        withdrawRepository.update(withdraw);
        //申请三方代付
        this.pullWithdrawal(withdraw);
        return withdraw;
    }

    @Override
    public Withdraw refuse(String id) {
        Withdraw withdraw = withdrawRepository.getById(id);
        if(withdraw == null){
            throw new NoFoundException();
        }
        if(withdraw.getStatus() != 1){
            throw new DataErrorException();
        }
        //审核不通过
        withdraw.setComment("不允许的提现操作");
        withdraw.setStatus(3);
        withdraw.setSuccess(2);
        //获取提现比例
        SystemConfig systemConfig = systemConfigAppService.get();
        //退还用户钻石
        BigDecimal score = withdraw.getMoney().multiply(systemConfig.getRechargeRatio());
        User user = userManagerService.searchByUsername(withdraw.getAccount().getUserName());
        //先退还用户积分
        user.setScore(user.getScore().add(score));
        user.setPrimeScore(user.getPrimeScore().add(score));
        userRepository.update(user);
        //再添加积分明细
        ScoreDetailed scoreDetailed = new ScoreDetailed();
        scoreDetailed.setUser(user);
        scoreDetailed.setNewScore(user.getScore());
        scoreDetailed.setScore(score);
        scoreDetailed.setActionType(6);
        scoreDetailedRepository.save(scoreDetailed);
        //更新提现记录
        withdrawRepository.update(withdraw);
        return withdraw;
    }
}
