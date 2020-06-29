package com.mfangsoft.zhuangjialong.app.webscokettest.listener.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.webscokettest.listener.MessageDelegateListener;
import com.mfangsoft.zhuangjialong.app.webscokettest.service.ITestWebScoketService;

public class MessageDelegateListenerImpl implements MessageDelegateListener {
	
	@Autowired
	private ITestWebScoketService testWebScoketService;

	@Override
	public String handleMessage(Serializable message) {
		// TODO Auto-generated method stub
		
		return  message.toString();
	}

	
}
