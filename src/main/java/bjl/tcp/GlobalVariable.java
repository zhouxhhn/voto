package bjl.tcp;

import bjl.domain.model.robot.Robot;
import bjl.websocket.command.GameStatus;
import bjl.websocket.command.WSMessage;
import io.netty.channel.Channel;

import javax.websocket.Session;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局变量
 * Created by zhangjin on 2017/10/27.
 */
public class GlobalVariable {

    /**
     * 验证码
     */
    private static Map<String,Object[]> codeMap = new ConcurrentHashMap<>();
    /**
     * 保存全局netty ChannelHandler变量
     */
    private static Map<String, Channel> map = new ConcurrentHashMap<>();

    /**
     * webSocket session
     */
    private static Map<String,Session> wsSession = Collections.synchronizedMap(new HashMap<>());
    /**
     * 保存玩家下注信息
     */
    private static Map<Integer,Map<String, WSMessage>> betMap = Collections.synchronizedMap(new LinkedHashMap<>());
    /**
     * 记录机器人信息
     */
    private static Map<Integer,Map<String, Robot>> robotMap = Collections.synchronizedMap(new LinkedHashMap<>());
    /**
     * 总计
     */
    private static Map<String, BigDecimal[]> totalMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * 游戏状态
     */
    private static Map<Integer,GameStatus> gameStatusMap = Collections.synchronizedMap(new HashMap<>());

    public static Map<Integer, Map<String, Robot>> getRobotMap() {
        return robotMap;
    }

    public static Map<String, Object[]> getCodeMap() {
        return codeMap;
    }

    public static Map<Integer, GameStatus> getGameStatusMap() {
        return gameStatusMap;
    }

    public static Map<String, BigDecimal[]> getTotalMap() {
        return totalMap;
    }

    public static Map<String, Session> getWsSession() {
        return wsSession;
    }

    public static void setWsSession(Map<String, Session> wsSession) {
        GlobalVariable.wsSession = wsSession;
    }

    public static Map<Integer, Map<String, WSMessage>> getBetMap() {
        return betMap;
    }

    public static Map<String, Channel> getChannelHandler() {
        return map;
    }

    public static void addChannelHandler(String id,Channel channelHandler) {
        map.put(id,channelHandler);
    }

    public static void removeChannelHandler(String id) {
        map.remove(id);
    }
}
