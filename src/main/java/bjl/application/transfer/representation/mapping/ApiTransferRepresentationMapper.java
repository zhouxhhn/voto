package bjl.application.transfer.representation.mapping;

import bjl.application.transfer.representation.ApiTransferRepresentation;
import bjl.domain.model.transfer.Transfer;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Component
public class ApiTransferRepresentationMapper extends CustomMapper<Transfer, ApiTransferRepresentation> {

    public void mapAtoB(Transfer transfer, ApiTransferRepresentation representation, MappingContext context) {

        representation.setBalance(transfer.getTransferScore());
        representation.setGold(transfer.getScore());
        representation.setTime(transfer.getCreateDate().getTime());
        representation.setUserid(transfer.getTransfer().getUserName());
    }
}
