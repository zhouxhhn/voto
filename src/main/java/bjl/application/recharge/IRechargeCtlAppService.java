package bjl.application.recharge;

import bjl.application.recharge.command.EditRechargeCtlCommand;
import bjl.domain.model.recharge.RechargeCtl;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by zhangjin on 2017/9/29.
 */
public interface IRechargeCtlAppService {

    JSONObject get(JSONObject jsonObject);

    RechargeCtl getRechargeCtl();

    RechargeCtl edit(EditRechargeCtlCommand command);

}
