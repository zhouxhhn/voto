package bjl.application.bank;

import bjl.application.bank.command.CreateBankCommand;
import bjl.application.bank.command.ListBankCommand;
import bjl.application.bank.representation.ApiBankDtlRepresentation;
import bjl.application.bank.representation.ApiBankRepresentation;
import bjl.application.bank.representation.BankRepresentation;
import bjl.core.api.ApiReturnCode;
import bjl.core.api.ResultData;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.bank.BankDtl;
import bjl.domain.service.bank.IBankService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjin on 2017/9/6.
 */
@Service("bankAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BankAppService implements IBankAppService {

    @Autowired
    private IBankService bankService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public JSONObject create(CreateBankCommand command) {
        return bankService.create(command);
    }

    @Override
    public Pagination<BankRepresentation> pagination(ListBankCommand command) {
        command.verifyPage();
        command.verifyPageSize(18);
        Pagination<Bank> pagination = bankService.pagination(command);
        List<BankRepresentation> data = mappingService.mapAsList(pagination.getData(),BankRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public Bank unbound(String id) {
        return bankService.unbound(id);
    }

    @Override
    public ResultData info(String username) {
        Bank bank = bankService.info(username);
        if(bank == null){
            return new ResultData(false, ApiReturnCode.NO_FOUND);
        }
        ApiBankRepresentation representation = mappingService.map(bank, ApiBankRepresentation.class,false);

        return new ResultData(true, JSON.toJSONString(representation));
    }

    @Override
    public JSONObject info(JSONObject jsonObject) {
        return bankService.info(jsonObject);
    }

    @Override
    public List<ApiBankDtlRepresentation> bankDtl(String username) {
        List<BankDtl> list = bankService.bankDtl(username);
        if(list == null || list.size()<1){
            return null;
        }
        List<ApiBankDtlRepresentation> newList = new ArrayList<>();
        for(BankDtl representation : list){
            int money = representation.getMoney().multiply(BigDecimal.valueOf(100)).intValue();
            if("2".equals(representation.getType()) && representation.getStatus() == 2 || representation.getStatus() == 3){
                newList.add(new ApiBankDtlRepresentation(representation.getCreateDate(),money,representation.getType(),4));
            }
            newList.add(new ApiBankDtlRepresentation(representation.getCreateDate(),money,representation.getType(),representation.getStatus()));
            if(newList.size() >= 50){
                break;
            }
        }
        return newList;
    }
}
