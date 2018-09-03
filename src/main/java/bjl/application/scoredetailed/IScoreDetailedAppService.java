package bjl.application.scoredetailed;

import com.alibaba.fastjson.JSONObject;

import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IScoreDetailedAppService {

    User create(CreateScoreDetailedCommand command);

    JSONObject list(ListGameDetailedCommand command);
}
