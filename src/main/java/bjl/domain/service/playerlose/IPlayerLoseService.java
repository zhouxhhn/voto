package bjl.domain.service.playerlose;

import bjl.application.playerlose.command.ListPlayerLoseCommand;
import bjl.application.playerlose.command.TotalPlayerLoseCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;


/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IPlayerLoseService {

    Pagination<GameDetailed> list(ListPlayerLoseCommand command);

    TotalPlayerLoseCommand count(ListPlayerLoseCommand command);

}
