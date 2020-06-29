package com.mfangsoft.zhuangjialong.common.webscoket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.mfangsoft.zhuangjialong.app.personalCenter.service.PersonalCenterService;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.webscoket.BaseWebScoketHandler;

@Configuration
@EnableWebSocket

public class SysWebscoketHandler extends BaseWebScoketHandler {

	// 保存用户websockt
	public static List<WebSocketSession> users = new ArrayList<WebSocketSession>();

	@Autowired
	PersonalCenterService personalCenterService;
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		// long customer_id = Long.parseLong("" +
		// session.getAttributes().get(SysConstant.WEBSOCKET_CUSTOMERID));
		// if(customer_id > 0 ){
		users.add(session);
		System.out.println("------------建立连接------------------------------" + users.size());
		// }
//		sendMessageToUsers(new TextMessage(CharBuffer.wrap("testsetset"), true));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		long customer_id = (long)session.getAttributes().get(SysConstant.WEBSOCKET_CUSTOMERID);
		//发送未读消息个数
		sendMessageToUsers(new TextMessage(personalCenterService.selectUnReadByCustomerId(customer_id)+ "", true));
		super.handleMessage(session, message);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		users.remove(session);
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public static void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public static void sendMessageToUser(long user_id, TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.getAttributes().get(SysConstant.WEBSOCKET_CUSTOMERID).equals(user_id)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
