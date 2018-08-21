package bjl.websocket;

import bjl.tcp.GlobalVariable;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 越南厅 websocket
 * Created by zhangjin on 2017/12/25.
 */
@ServerEndpoint(value = "/hall/vietnam", configurator = SpringConfigurator.class)
public class VietnamWS {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static List<Session> sessionList = new ArrayList<>();

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        //事件处理
        JSONObject result = new EventProcess().eventMessage(message);

        //webSocket返回信息
        if(result != null){
            session.getBasicRemote().sendText(result.toJSONString());
        }

    }

    @OnOpen
    public void onOpen(Session session) {

        logger.info("越南厅webService已连接...");


        try{
            //先取出之前的连接
//            Session session1  = GlobalVariable.getWsSession().get("1");
//            if(session1 != null){
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("text","连接已被关闭");
//                session1.getBasicRemote().sendText(jsonObject.toJSONString());
//        }
            //保存session
            GlobalVariable.getWsSession().put("2",session);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClose
    public void onClose(Session session) {
        logger.info("越南厅webService连接已断开...");
        GlobalVariable.getWsSession().remove("2");
    }

    @OnError
    public void onError(Throwable throwable) {
    }


}
