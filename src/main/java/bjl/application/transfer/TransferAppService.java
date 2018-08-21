package bjl.application.transfer;

import bjl.application.account.IAccountAppService;
import bjl.application.transfer.command.CreateTransferCommand;
import bjl.application.transfer.command.ListTransferCommand;
import bjl.application.transfer.representation.ApiTransferRepresentation;
import bjl.core.common.PasswordHelper;
import bjl.core.mapping.IMappingService;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.transfer.Transfer;
import bjl.domain.service.transfer.ITransferService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("transferAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransferAppService implements ITransferAppService{

    @Autowired
    private ITransferService transferService;
    @Autowired
    private IMappingService mappingService;
    @Autowired
    private IAccountAppService accountAppService;

    @Override
    public Transfer create(CreateTransferCommand command) {
        return transferService.create(command);
    }

    @Override
    public JSONObject transfer(JSONObject jsonObject) {

        CreateTransferCommand command = new CreateTransferCommand();
        command.setScore(jsonObject.getBigDecimal("gold"));
        command.setTransfer(jsonObject.getString("userid1"));
        command.setReceive(jsonObject.getString("userid2"));
        Account account = accountAppService.searchByUsername(jsonObject.getString("userid1"));
        if(account == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }else {
            if (CoreStringUtils.isEmpty(jsonObject.getString("pwd")) || !PasswordHelper.equalsBankPwd(account, jsonObject.getString("pwd"))) {
                jsonObject.put("code", 1);
                jsonObject.put("errmsg","密码错误");
                return jsonObject;
            }
        }

        Transfer transfer = transferService.create(command);

        if(transfer == null ){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }
        if(transfer.getTransfer() == null && transfer.getReceive() == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","积分不足");
            jsonObject.put("gold",transfer.getScore());
            return jsonObject;
        }
        jsonObject.put("code",0);
        jsonObject.put("errmsg","申请转账成功");
        jsonObject.put("gold",transfer.getTransferScore());

        return jsonObject;
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {

        String userId = jsonObject.getString("userid");
        List<Transfer> data = transferService.list(userId);
        if(data == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","用户不存在");
            return jsonObject;
        }

        List<ApiTransferRepresentation> list = new ArrayList<>();
        for(Transfer transfer : data){
            ApiTransferRepresentation representation = new ApiTransferRepresentation();
            if(transfer.getTransfer().getUserName().equals(userId)){

                representation.setUserid(transfer.getReceive().getUserName());
                representation.setGold(transfer.getScore().multiply(BigDecimal.valueOf(-1)));
                representation.setBalance(transfer.getTransferScore());
            }else {
                representation.setUserid(transfer.getTransfer().getUserName());
                representation.setGold(transfer.getScore());
                representation.setBalance(transfer.getReceiveScore());
            }
            representation.setTime(transfer.getCreateDate().getTime());
            list.add(representation);
        }

        jsonObject.put("code",0);
        jsonObject.put("errmsg","查询成功");
        jsonObject.put("records",list);

        return jsonObject;
    }

    @Override
    public Pagination<Transfer> pagination(ListTransferCommand command) {

        command.verifyPage();
        command.setPageSize(18);

        return transferService.pagination(command);
    }

    @Override
    public Transfer pass(String id) {

        return transferService.pass(id);
    }

    @Override
    public Transfer refuse(String id) {

        return transferService.refuse(id);
    }
}
