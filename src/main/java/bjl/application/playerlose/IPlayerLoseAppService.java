package bjl.application.playerlose;

import bjl.application.playerlose.command.ListPlayerLoseCommand;
import bjl.application.playerlose.command.TotalPlayerLoseCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IPlayerLoseAppService {

    Pagination<GameDetailed> pagination(ListPlayerLoseCommand command);

    TotalPlayerLoseCommand total(ListPlayerLoseCommand command);
}
