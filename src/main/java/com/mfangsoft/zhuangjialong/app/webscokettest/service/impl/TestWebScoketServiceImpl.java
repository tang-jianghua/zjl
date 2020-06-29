package com.mfangsoft.zhuangjialong.app.webscokettest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.mfangsoft.zhuangjialong.app.webscokettest.mapper.IRedisMapper;
import com.mfangsoft.zhuangjialong.app.webscokettest.service.ITestWebScoketService;
@Service
public class TestWebScoketServiceImpl implements ITestWebScoketService {
	@Autowired
	private  IRedisMapper mapper;



	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		mapper.pushMessage("key_webscoket", message);
	}



	@Override
	public String getMessage(String key) {
		// TODO Auto-generated method stub
		return mapper.popMessage("key_webscoket");
	}
	
	

}
