package bjl.application.recharge;

import bjl.application.recharge.command.EditRechargeCtlCommand;
import bjl.domain.model.recharge.RechargeCtl;
import bjl.domain.service.recharge.IRechargeCtlService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjin on 2017/9/29.
 */
@Service("rechargeCtlAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RechargeCtlAppService implements IRechargeCtlAppService {

    @Autowired
    private IRechargeCtlService rechargeCtlService;

    @Override
    public JSONObject get(JSONObject jsonObject) {

        RechargeCtl rechargeCtl = rechargeCtlService.get();
        List<Integer> list = new ArrayList<>();
        if(rechargeCtl != null){

            list.add(rechargeCtl.getWeChat());
            list.add(rechargeCtl.getAliPay());
            list.add(rechargeCtl.getQqCode());
            list.add(rechargeCtl.getBank());
            list.add(rechargeCtl.getAliCode());
            list.add(rechargeCtl.getWeCode());
            list.add(rechargeCtl.getQqPay());
        }else {
            list.add(1);
            list.add(1);
            list.add(1);
            list.add(1);
            list.add(1);
            list.add(1);
            list.add(1);
        }
        jsonObject.put("data",list);
        jsonObject.put("code",0);
        jsonObject.put("errmsg","成功");
        return jsonObject;
    }

    @Override
    public RechargeCtl getRechargeCtl() {
        return rechargeCtlService.get();
    }

    @Override
    public RechargeCtl edit(EditRechargeCtlCommand command) {
       return rechargeCtlService.edit(command);
    }

}
