package com.mfangsoft.zhuangjialong.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.UserSessionUtil;

public class ServerLoginInterceptor  extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		 HttpSession session=request.getSession();
		 if(request.getSession().getAttribute(SysConstant.USER_INFO)!=null){
			 
			 UserSessionUtil.setUserSession(session);
			 
		 }else{
			 
			 java.io.Writer out   =response.getWriter();
				//request.getContextPath();
				String path="<script>window.location.href = \"http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/login"+"\";</script>";
				 out.write(path);
				 
				 out.flush();
				 out.close();
				 return false;
			 //response.sendRedirect("index.jsp");
		 }
		
		return true;
	}
	
	
	
	
	

}
