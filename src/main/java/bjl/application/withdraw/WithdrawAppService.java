package bjl.application.withdraw;

import bjl.application.withdraw.command.ListWithdrawCommand;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.withdraw.Withdraw;
import bjl.domain.service.withdraw.IWithdrawService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("withdrawAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

public class WithdrawAppService implements IWithdrawAppService{

    @Autowired
    private IWithdrawService withdrawService;

    @Override
    public JSONObject applyWithdraw(JSONObject jsonObject) {

        if(CoreStringUtils.isEmpty(jsonObject.getString("userid"))){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        return withdrawService.applyWithdraw(jsonObject);
    }

    @Override
    public Pagination<Withdraw> pagination(ListWithdrawCommand command) {
        command.verifyPage();
        command.setPageSize(18);
        return withdrawService.pagination(command);
    }

    @Override
    public Withdraw pass(String id) {
        return withdrawService.pass(id);
    }

    @Override
    public Withdraw refuse(String id) {
        return withdrawService.refuse(id);
    }
}
