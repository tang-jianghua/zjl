package com.mfangsoft.zhuangjialong.core.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.UserSessionUtil;
@WebFilter(filterName="htmlfilter",urlPatterns={"/views/*","/server/*"})
public class HtmlFilter implements Filter {

	
	
	private  final  static  Logger  log =LoggerFactory.getLogger(HtmlFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest  httpServletRequest = (HttpServletRequest) request;
		   
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		HttpSession session=httpServletRequest.getSession();
		
		
		
		log.info("serverName:"+httpServletRequest.getServerName());
		
		log.info("ip:"+httpServletRequest.getRemoteAddr());
		
		log.info("localAddr:"+httpServletRequest.getLocalAddr());
		
		log.info("requestUrl:"+httpServletRequest.getRequestURL()+"   httpMethod:"+httpServletRequest.getMethod());
		
		Enumeration<String> e= httpServletRequest.getHeaderNames();
		
		while (e.hasMoreElements()) {
			String string = (String) e.nextElement();
			
			
			log.info(string+":"+httpServletRequest.getHeader(string));
			
		}
		
		
		if(session.getAttribute(SysConstant.USER_INFO)==null){
			java.io.Writer out   =httpServletResponse.getWriter();
			String path="";
			if(httpServletRequest.getServerName().equals("myserver")){
				 path="<script>window.location.href = \"http://www.kaolaj.com"+httpServletRequest.getContextPath()+"/"+"\";</script>";
				
			}else{
				
				 path="<script>window.location.href = \"http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath()+"/login"+"\";</script>";
				
			}
			out.write(path);
			out.flush();
			out.close();
			
			//httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
		}else{
			
			UserSessionUtil.setUserSession(session);
		}
		
		
		chain.doFilter(request, response);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
