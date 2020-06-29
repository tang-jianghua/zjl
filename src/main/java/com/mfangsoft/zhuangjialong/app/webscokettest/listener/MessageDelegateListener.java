package com.mfangsoft.zhuangjialong.app.webscokettest.listener;

import java.io.Serializable;
import java.util.Map;

public interface MessageDelegateListener {
	
	  String handleMessage(Serializable message);
	 
}
