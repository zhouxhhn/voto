package bjl.domain.service.scoredetailed;

import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IScoreDetailedService {

    User create(CreateScoreDetailedCommand command);

    Pagination<ScoreDetailed> pagination(ListGameDetailedCommand command);
}
