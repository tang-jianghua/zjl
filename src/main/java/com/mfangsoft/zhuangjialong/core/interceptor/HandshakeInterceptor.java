package com.mfangsoft.zhuangjialong.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		 if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
			 request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
		
		
//		if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//            HttpSession session = servletRequest.getServletRequest().getSession(false);
//            if (session != null) {
//                //使用CUSTOMER_ID区分WebSocketHandler，以便定向发送消息
//                String customer_id = (String) session.getAttribute(SysConstant.CUSTOMER_ID);
////                attributes.put(SysConstant.WEBSOCKET_USERNAME,userName);
//                attributes.put(SysConstant.WEBSOCKET_CUSTOMERID,customer_id);
//            }
//        }
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}
	
}
