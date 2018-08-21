package bjl.domain.service.recharge;

import bjl.application.recharge.command.EditRechargeCtlCommand;
import bjl.domain.model.recharge.IRechargeCtlRepository;
import bjl.domain.model.recharge.RechargeCtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zhangjin on 2017/9/29
 */
@Service("rechargeCtlService")
public class RechargeCtlService implements IRechargeCtlService {

    @Autowired
    private IRechargeCtlRepository<RechargeCtl, String> rechargeCtlRepository;

    /**
     * 获取支付控制信息
     * @return
     */
    @Override
    public RechargeCtl get() {
        List<RechargeCtl> list = rechargeCtlRepository.list(null,null);
        if(list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    /**
     * 更新支付控制信息
     * @param command
     */
    @Override
    public RechargeCtl edit(EditRechargeCtlCommand command) {
        List<RechargeCtl> list = rechargeCtlRepository.list(null,null);
        RechargeCtl rechargeCtl = new RechargeCtl();
        if(list.size() == 1){
            rechargeCtl = list.get(0);
        }
        rechargeCtl.setAliCode(command.getAliCode());
        rechargeCtl.setAliPay(command.getAliPay());
        rechargeCtl.setBank(command.getBank());
        rechargeCtl.setQqCode(command.getQqCode());
        rechargeCtl.setQqPay(command.getQqPay());
        rechargeCtl.setWeChat(command.getWeChat());
        rechargeCtl.setWeCode(command.getWeCode());
        rechargeCtl.setAliPayPass(command.getAliPayPass());
        rechargeCtl.setWeChatPass(command.getWeChatPass());
        rechargeCtl.setBankPass(command.getBankPass());
        rechargeCtl.setQQPayPass(command.getQQPayPass());
        if(list.size() == 1){
            rechargeCtlRepository.update(rechargeCtl);
        }else {
            rechargeCtlRepository.save(rechargeCtl);
        }
        return rechargeCtl;
    }
}
