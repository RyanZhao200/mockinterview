package com.debuggor.mockinterview.interview.websocket;

import com.alibaba.fastjson.JSON;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.interview.bean.ChatInterview;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 聊天服务端
 * https://blog.csdn.net/qq_38455201/article/details/80374712
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Component
@ServerEndpoint("/chat/{fid}/{acceptType}/{iid}")
public class WebSocketChatServer {

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<String, WebSocketChatServer> onlineSessions = new ConcurrentHashMap<>();

    private Session session;
    private Integer sendUid;
    private Integer acceptUid;
    private String sendType;


    /**
     * 当客户端打开连接：1.添加会话对象 2.给对方发送在线的消息
     * 用“邮箱”标注聊天的双方
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("fid") Integer fid,
                       @PathParam("acceptType") String sendType,
                       @PathParam("iid") Integer iid) {
        System.out.println(fid + "-" + sendType + "-" + iid);
        this.session = session;
        this.sendType = sendType;
        if (UserEnum.INTERVIEWER.key.equals(sendType)) {
            this.sendUid = iid;
            this.acceptUid = fid;
            String sendKey = sendUid + "" + sendType;
            onlineSessions.put(sendKey, this);
            String acceptKey = acceptUid + "" + UserEnum.FINDER.key;
            sendMessageTo(sendKey, acceptKey, ChatInterview.jsonStr("已上线！", iid, UserEnum.INTERVIEWER.key, fid, UserEnum.FINDER.key, new Date()));
        } else {
            this.sendUid = fid;
            this.acceptUid = iid;
            String sendKey = sendUid + "" + sendType;
            onlineSessions.put(sendKey, this);
            String acceptKey = acceptUid + "" + UserEnum.INTERVIEWER.key;
            sendMessageTo(sendKey, acceptKey, ChatInterview.jsonStr("已上线！", fid, UserEnum.FINDER.key, iid, UserEnum.INTERVIEWER.key, new Date()));
        }
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给对方
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        ChatInterview chatInterview = JSON.parseObject(jsonStr, ChatInterview.class);
        String sendKey = null;
        String acceptKey = null;
        if (UserEnum.INTERVIEWER.key.equals(chatInterview.getSendType())) {
            sendKey = sendUid + "" + UserEnum.INTERVIEWER.key;
            acceptKey = acceptUid + "" + UserEnum.FINDER.key;
        } else {
            sendKey = sendUid + "" + UserEnum.FINDER.key;
            acceptKey = acceptUid + "" + UserEnum.INTERVIEWER.key;
        }
        sendMessageTo(sendKey, acceptKey, ChatInterview.jsonStr(chatInterview.getMessage(), chatInterview.getSendUid(), chatInterview.getSendType(), chatInterview.getAcceptUid(), chatInterview.getAcceptType(), new Date()));
    }

    /**
     * 当关闭连接：1.移除会话对象 2.给对方发送下线的消息
     */
    @OnClose
    public void onClose(Session session) {
        String sendKey = sendUid + "" + sendType;
        onlineSessions.remove(sendKey);

/*        String acceptKey = null;
        if (UserEnum.INTERVIEWER.key.equals(sendType)) {
            sendKey = sendUid + "" + sendType;
            acceptKey = acceptUid + "" + UserEnum.FINDER.key;
            sendMessageTo(sendKey, acceptKey, ChatInterview.jsonStr("已下线！", sendUid, UserEnum.INTERVIEWER.key, acceptUid, UserEnum.FINDER.key, new Date()));
        } else {
            sendKey = sendUid + "" + sendType;
            acceptKey = acceptUid + "" + UserEnum.INTERVIEWER.key;
            sendMessageTo(sendKey, acceptKey, ChatInterview.jsonStr("已下线！", sendUid, UserEnum.FINDER.key, acceptUid, UserEnum.INTERVIEWER.key, new Date()));
        }
*/
    }

    /**
     * 当通信发生异常：打印错误日志
     * 当关闭时，会发生错误，屏蔽错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        error.printStackTrace();
    }

    /**
     * 发送给个人
     */
    private void sendMessageTo(String sendKey, String acceptKey, String msg) {
        WebSocketChatServer accept = onlineSessions.get(acceptKey);
        WebSocketChatServer send = onlineSessions.get(sendKey);
        try {
            if (accept != null) {
                accept.session.getAsyncRemote().sendText(msg);
            }
            if (send != null) {
                send.session.getAsyncRemote().sendText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
