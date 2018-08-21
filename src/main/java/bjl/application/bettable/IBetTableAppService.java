package bjl.application.bettable;

import bjl.websocket.command.WSMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2018/4/28
 */
public interface IBetTableAppService {

    void create(Integer hallType, Integer boots, Integer games, Map<String,WSMessage> map);

    List<Object[]> get(Integer hallType, Integer boots, Integer games, String dateStr);

}
