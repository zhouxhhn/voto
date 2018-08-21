package bjl.application.bank.representation.mapping;

import bjl.application.bank.representation.ApiBankRepresentation;
import bjl.domain.model.bank.Bank;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/9/7.
 */
@Component
public class ApiBankRepresentationMapper extends CustomMapper<Bank, ApiBankRepresentation> {

    public void mapAtoB(Bank bank, ApiBankRepresentation representation, MappingContext context) {

        if(bank.getBankAccountNo() != null){
            StringBuffer str = new StringBuffer(bank.getBankAccountNo());
            for(int i=0;i<str.length()-4;i++){
                str.setCharAt(i,'*');
            }
            representation.setCardnum(str.toString());
        }
        representation.setBanktype(bank.getBankName());
        representation.setCardtype(bank.getBankCardType());
    }
}
