package bjl.application.bank.representation.mapping;

import bjl.application.bank.representation.BankRepresentation;
import bjl.domain.model.bank.Bank;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/9/7.
 */
@Component
public class BankRepresentationMapper extends CustomMapper<Bank, BankRepresentation> {

    public void mapAtoB(Bank bank, BankRepresentation representation, MappingContext context) {

        representation.setToken(bank.getAccount().getToken());
        representation.setName(bank.getAccount().getName());

    }

}
