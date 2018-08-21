package bjl.application.withdraw;

import bjl.application.withdraw.command.ListWithdrawCommand;
import bjl.domain.model.withdraw.Withdraw;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface IWithdrawAppService {

    JSONObject applyWithdraw(JSONObject jsonObject);

    Pagination<Withdraw> pagination(ListWithdrawCommand command);

    Withdraw pass(String id);

    Withdraw refuse(String id);

}
