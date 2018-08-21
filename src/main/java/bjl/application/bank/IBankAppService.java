package bjl.application.bank;

import bjl.application.bank.command.CreateBankCommand;
import bjl.application.bank.command.ListBankCommand;
import bjl.application.bank.representation.ApiBankDtlRepresentation;
import bjl.application.bank.representation.BankRepresentation;
import bjl.core.api.ResultData;
import bjl.domain.model.bank.Bank;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by zhangjin on 2017/9/6.
 */
public interface IBankAppService {

    JSONObject create(CreateBankCommand command);

    Pagination<BankRepresentation> pagination(ListBankCommand command);

    Bank unbound(String id);

    ResultData info(String username);

    JSONObject info(JSONObject jsonObject);

    List<ApiBankDtlRepresentation> bankDtl(String username);
}
