package com.mfangsoft.zhuangjialong.app.webscokettest.mapper;

import java.io.Serializable;

public interface IRedisMapper {
	
	 void sendMessage(String channel, Serializable message);
	 
	 
	 void pushMessage(String key, String message);
	 
	 
	 String popMessage(String key);

}
