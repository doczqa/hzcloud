package com.hzcloud.websocket;

import com.alibaba.fastjson.JSONObject;
import com.hzcloud.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.common.NameUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 前段页面的websocket,处理通知公告等消息
 */
@Slf4j
@Component
@ServerEndpoint("/web")
public class WebSocketServer {

    /** 记录当前在线连接数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /** 客户端状态机*/
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private static Map<Session, String> reverseClients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet();
        String userId = reverseClients.get(session);
        if (userId != null) {
            reverseClients.remove(session);
            clients.remove(userId);
        }
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
//        MessageHandler messageHandler = SpringUtils.getBean(MessageHandler.class);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(message);
        String cmd = jsonObject.getString("cmd");
        if ("login".equals(cmd)) {
            String userId = jsonObject.getString("userId");
            clients.put(userId, session);
            reverseClients.put(session, userId);
        }
        sendMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    public void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e);
        }
    }

    /**
     * 发送消息给所有client
     * @param message
     * @param fromSession
     */
    public void sendMessageToAllClient(String message, Session fromSession) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            // 排除掉自己
//            if (fromSession != null && !fromSession.getId().equals(toSession.getId())) {
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                sendMessage(message, toSession);
//            }
        }
    }
}
