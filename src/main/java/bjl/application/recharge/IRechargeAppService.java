package bjl.application.recharge;

import bjl.application.recharge.command.CreateRechargeCommand;
import bjl.application.recharge.command.ListRechargeCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.pay.PayNotify;
import bjl.domain.model.recharge.Recharge;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface IRechargeAppService {

    JSONObject list(JSONObject jsonObject);

    Recharge createOrder(CreateRechargeCommand command);

    boolean payNotify(PayNotify notify);

    Pagination<Recharge> pagination(ListRechargeCommand command);

    Map<String, BigDecimal> sum(Date date);

}
