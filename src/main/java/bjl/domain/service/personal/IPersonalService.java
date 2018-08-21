package bjl.domain.service.personal;

import bjl.application.personal.command.ListPersonalCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IPersonalService {

    Pagination<GameDetailed> pagination(ListPersonalCommand command);

    Object[] total(ListPersonalCommand command);
}
