package bjl.domain.service.recharge;

import bjl.application.recharge.command.EditRechargeCtlCommand;
import bjl.domain.model.recharge.RechargeCtl;

/**
 * Created by zhangjin on 2017/9/29.
 */
public interface IRechargeCtlService {

    RechargeCtl get();

    RechargeCtl edit(EditRechargeCtlCommand command);
}
