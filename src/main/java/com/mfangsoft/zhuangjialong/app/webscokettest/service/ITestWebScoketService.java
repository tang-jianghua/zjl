package com.mfangsoft.zhuangjialong.app.webscokettest.service;

import com.mfangsoft.zhuangjialong.core.exception.ServiceException;

public interface ITestWebScoketService {
	
	void sendMessage(String message) throws ServiceException;
	
	String getMessage(String key) throws ServiceException;
 
}
