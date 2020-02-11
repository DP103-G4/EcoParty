package tw.dp103g4.websocket;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;


@ServerEndpoint("/SocketServer/{userId}")
public class SocketServer {
	private static Map<Integer, Session> sessionMap = new ConcurrentHashMap<Integer, Session>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("userId") Integer userId, Session userSession) throws IOException {
		//將上線的人加到socket
		sessionMap.put(userId, userSession); 
		Set<Integer> userIds = sessionMap.keySet();
		//好友上線消息
//		StateMsg stateMsg = new StateMsg("open", userId);
//		String stateMsgJson = gson.toJson(stateMsg);
//		Collection<Session> sessions = sessionMap.values();
//		for (Session session : sessions) {
//			if (session.isOpen()) {
//				session.getAsyncRemote().sendText(stateMsgJson);
//			}	
//		}
		String text = String.format("Session ID = %s, conected; userId = %s%nusers: %s", userSession.getId(),
				userId, userIds);
		System.out.println(text);
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMsg chatMsg = gson.fromJson(message, ChatMsg.class);
		int receiver = chatMsg.getReceiver();
		Session receiverSession = sessionMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
		}else {
			sessionMap.remove("receiver");
		}
		System.out.println("Message received: " + message);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Integer userIdClose = null;
		//將下線的人移除
		Set<Integer> userIds = sessionMap.keySet();
		for(Integer userId : userIds) {
			if (sessionMap.get(userId) == userSession) {
				userIdClose = userId;
				sessionMap.remove(userId);
				break;
			}
		}
		//好友離線消息
//		if (userIdClose != null) {
//			StateMsg stateMsg = new StateMsg("close", userIdClose);
//			String stateMsgJson = gson.toJson(stateMsg);
//			Collection<Session> sessions = sessionMap.values();
//			for(Session session : sessions) {
//				session.getAsyncRemote().sendText(stateMsgJson);
//			}
//		}
		String text = String.format("session ID = %s, disconnentes; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(),userIds);
		System.out.print(text);
	}
}
