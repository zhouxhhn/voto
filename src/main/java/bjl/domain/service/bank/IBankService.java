package bjl.domain.service.bank;

import bjl.application.bank.command.CreateBankCommand;
import bjl.application.bank.command.ListBankCommand;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.bank.BankDtl;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by zhangjin on 2017/9/6.
 */
public interface IBankService {

    JSONObject create(CreateBankCommand command);

    Pagination<Bank> pagination(ListBankCommand command);

    Bank unbound(String id);

    Bank info(String username);

    JSONObject info(JSONObject jsonObject);

    List<BankDtl> bankDtl(String username);
}
