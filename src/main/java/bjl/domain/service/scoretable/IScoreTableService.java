package bjl.domain.service.scoretable;

import bjl.websocket.command.WSMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2018/4/28
 */
public interface IScoreTableService {

    void create(Integer hallType, Integer boots, Integer games, Map<String,WSMessage> map);

    List<Object[]> get(Integer hallType, Integer boots, Integer games, String dateStr);
}
