package com.mfangsoft.zhuangjialong.common.utils;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mfangsoft.zhuangjialong.common.context.UserContext;

public class UserSessionUtil {
	
	private final static Logger log = LoggerFactory.getLogger(UserContext.class);
	// 用于存放用戶session
	private static final ThreadLocal<HttpSession> local = new ThreadLocal<HttpSession>();

    public static void setUserSession(HttpSession session) {  
    	
        local.set(session);  
       
    }  
  
    public static HttpSession getUserSession() {  
    	
        return local.get();  
    }  
    
    public static void remove()
    
    {
    	local.remove();
    }
	
}
