package bjl.application.transfer;

import bjl.application.transfer.command.CreateTransferCommand;
import bjl.application.transfer.command.ListTransferCommand;
import bjl.domain.model.transfer.Transfer;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface ITransferAppService {

    Transfer create(CreateTransferCommand command);

    JSONObject transfer(JSONObject jsonObject);

    JSONObject list(JSONObject jsonObject);

    Pagination<Transfer> pagination(ListTransferCommand command);

    Transfer pass(String id);

    Transfer refuse(String id);
}
