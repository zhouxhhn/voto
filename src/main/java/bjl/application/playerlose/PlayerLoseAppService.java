package bjl.application.playerlose;

import bjl.application.playerlose.command.ListPlayerLoseCommand;
import bjl.application.playerlose.command.TotalPlayerLoseCommand;
import bjl.application.playerlose.reprensentation.PlayerLoseRepresentation;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.service.playerlose.IPlayerLoseService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("playerLoseAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PlayerLoseAppService implements IPlayerLoseAppService {


    @Autowired
    private IPlayerLoseService playerLoseService;

    @Override
    public Pagination<GameDetailed> pagination(ListPlayerLoseCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return playerLoseService.list(command);
    }

    @Override
    public TotalPlayerLoseCommand total(ListPlayerLoseCommand command) {

        return playerLoseService.count(command);
    }
}
