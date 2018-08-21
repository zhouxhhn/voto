package bjl.application.scoredetailed;

import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.domain.model.user.User;
import bjl.domain.service.scoredetailed.IScoreDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("scoreDetailedAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ScoreDetailedAppService implements IScoreDetailedAppService{

    @Autowired
    private IScoreDetailedService scoreDetailedService;

    @Override
    public User create(CreateScoreDetailedCommand command) {
        synchronized (command.getUserId()){
            return scoreDetailedService.create(command);
        }
    }
}
