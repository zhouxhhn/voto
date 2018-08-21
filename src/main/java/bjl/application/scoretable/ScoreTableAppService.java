package bjl.application.scoretable;

import bjl.domain.service.scoretable.IScoreTableService;
import bjl.websocket.command.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2018/4/28
 */
@Service("scoreTableAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ScoreTableAppService implements IScoreTableAppService{

    @Autowired
    private IScoreTableService scoreTableService;

    @Override
    public void create(Integer hallType, Integer boots, Integer games, Map<String, WSMessage> map) {
        scoreTableService.create(hallType,boots,games,map);
    }

    @Override
    public List<Object[]> get(Integer hallType, Integer boots, Integer games, String dateStr) {
        return scoreTableService.get(hallType,boots,games,dateStr);
    }
}
