
package bjl.websocket;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: pengyi
 * Date: 15-12-30
 */

@ServerEndpoint(value = "/customer", configurator = SpringConfigurator.class)
public class CustomerService {

//    private final static Map<String, Session> users = new HashMap<>();
//    private final static Map<String, Session> customers = new HashMap<>();
//    private final static Map<String, String> users_customers = new HashMap<>();
//
//    @OnMessage
//    public void onMessage(String message, Session session)
//            throws IOException, InterruptedException {
//        Receive receive = JSON.parseObject(message, Receive.class);
//        switch (receive.getType()) {
//            //用户进入
//            case 1:
//                synchronized (users) {
//                    users.put(receive.getContent(), session);
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("messageType", "TEXT");
//                    jsonObject.put("content", "正在为您匹配在线客服。。。");
//                    session.getBasicRemote().sendText("\"type\":1,\"content\":" + jsonObject.toJSONString());
//                    synchronized (customers) {
//                        if (customers.size() > 0) {
//                            String[] keys = customers.keySet().toArray(new String[0]);
//                            String randomKey = keys[new Random().nextInt(keys.length)];
//                            customers.get(randomKey);
//                            synchronized (users_customers) {
//                                users_customers.put(receive.getContent(), randomKey);
//                                jsonObject.put("messageType", "TEXT");
//                                jsonObject.put("content", "客服" + randomKey + "为您服务！");
//                                session.getBasicRemote().sendText("\"type\":1,\"content\":" + jsonObject.toJSONString());
//                            }
//                        }
//                    }
//                }
//
//                break;
//            //客服进入
//            case 2:
//                synchronized (customers) {
//                    customers.put(receive.getContent(), session);
//                }
//                break;
//            //用户给客服发送消息
//            case 3:
//
//                break;
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        synchronized (users) {
//            for (Map.Entry<String, Session> entry : users.entrySet()) {
//                if (entry.getValue() == session) {
//                    users.remove(entry.getKey());
//                    return;
//                }
//            }
//            for (Map.Entry<String, Session> entry : customers.entrySet()) {
//                if (entry.getValue() == session) {
//                    customers.remove(entry.getKey());
//                    return;
//                }
//            }
//        }
//    }
//
//    @OnError
//    public void onError(Throwable throwable) {
//        throwable.printStackTrace();
//    }


    public final static List<Session> sessionList = new ArrayList<>();

    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException, InterruptedException {
    }

    @OnOpen
    public void onOpen(Session session) {
        synchronized (sessionList) {
            sessionList.add(session);
        }
    }

    @OnClose
    public void onClose(Session session) {
        synchronized (sessionList) {
            sessionList.remove(session);
        }
    }

    @OnError
    public void onError(Throwable throwable) {
    }
}

