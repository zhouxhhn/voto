package bjl.application.bettable;

import bjl.domain.service.bettable.IBetTableService;
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
@Service("betTableAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BetTableAppService implements IBetTableAppService{

    @Autowired
    private IBetTableService betTableService;

    @Override
    public void create(Integer hallType, Integer boots, Integer games, Map<String, WSMessage> map) {
        betTableService.create(hallType,boots,games,map);
    }

    @Override
    public List<Object[]> get(Integer hallType, Integer boots, Integer games, String dateStr) {
        return betTableService.get(hallType,boots,games,dateStr);
    }
}
