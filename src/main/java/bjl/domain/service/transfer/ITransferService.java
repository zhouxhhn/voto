package bjl.domain.service.transfer;

import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.transfer.command.CreateTransferCommand;
import bjl.application.transfer.command.ListTransferCommand;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.transfer.Transfer;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface ITransferService {

    Transfer create(CreateTransferCommand command);

    List<Transfer> list(String userId);

    Pagination<Transfer> pagination(ListTransferCommand command);

    Transfer pass(String id);

    Transfer refuse(String id);
}
