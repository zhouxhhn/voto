package bjl.domain.service.withdraw;

import bjl.application.withdraw.command.ListWithdrawCommand;
import bjl.domain.model.withdraw.Withdraw;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/23
 */
public interface IWithdrawService {

    JSONObject applyWithdraw(JSONObject jsonObject);

    Pagination<Withdraw> pagination(ListWithdrawCommand command);

    Withdraw pass(String id);

    Withdraw refuse(String id);

}
