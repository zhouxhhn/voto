package bjl.domain.service.scoredetailed;

import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.domain.model.user.User;

/**
 * Created by zhangjin on 2017/12/26.
 */
public interface IScoreDetailedService {

    User create(CreateScoreDetailedCommand command);
}
