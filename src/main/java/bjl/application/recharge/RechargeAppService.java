package bjl.application.recharge;

import bjl.application.recharge.command.CreateRechargeCommand;
import bjl.application.recharge.command.ListRechargeCommand;
import bjl.application.recharge.repensentation.ApiRechargeRepresentation;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.pay.PayNotify;
import bjl.domain.model.recharge.Recharge;
import bjl.domain.service.recharge.IRechargeService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("rechargeAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RechargeAppService implements IRechargeAppService{

    @Autowired
    private IRechargeService rechargeService;


    @Override
    public JSONObject list(JSONObject jsonObject) {

        if(CoreStringUtils.isEmpty(jsonObject.getString("userid"))){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        List<ApiRechargeRepresentation> list = new ArrayList<>();

        List<Object[]> list1 = rechargeService.list(jsonObject);
        for(Object[] objects : list1){
            if("1".equals(objects[4])){
                ApiRechargeRepresentation representation = new ApiRechargeRepresentation(((Date) objects[1]).getTime(),"充值",(Integer)objects[3] == 1 ? "成功":"失败", (BigDecimal) objects[2]);
                list.add(representation);
            } else {
                ApiRechargeRepresentation representation1 = new ApiRechargeRepresentation(((Date) objects[1]).getTime(),"提现",(Integer)objects[3] == 1 ? "成功":"失败", (BigDecimal) objects[2]);

                list.add(representation1);
            }
        }

        jsonObject.put("code",0);
        jsonObject.put("errmsg","查询充值明细成功");
        jsonObject.put("details",list);

        return jsonObject;
    }

    @Override
    public Recharge createOrder(CreateRechargeCommand command) {
        return rechargeService.createOrder(command);
    }

    @Override
    public boolean payNotify(PayNotify notify) {
        return rechargeService.payNotify(notify);
    }

    @Override
    public Pagination<Recharge> pagination(ListRechargeCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return rechargeService.pagination(command);
    }

    @Override
    public Map<String, BigDecimal> sum(Date date) {
        return rechargeService.sum(date);
    }
}
