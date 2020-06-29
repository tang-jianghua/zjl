package com.mfangsoft.zhuangjialong.app.webscokettest.handler;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.mfangsoft.zhuangjialong.app.webscokettest.listener.MessageDelegateListener;
import com.mfangsoft.zhuangjialong.app.webscokettest.service.ITestWebScoketService;
import com.mfangsoft.zhuangjialong.core.webscoket.BaseWebScoketHandler;

public class ReceiveHandler extends  BaseWebScoketHandler {
	
	@Autowired
	private ITestWebScoketService testWebScoketService;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		session.sendMessage(new TextMessage(testWebScoketService.getMessage("")));
	}

	
	
	

	


}
