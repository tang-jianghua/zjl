package com.mfangsoft.zhuangjialong.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.common.utils.TokenUtil;

public class IdentityTokenInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		

//		HttpSession session = request.getSession();
//		Object customer = session.getAttribute(SysConstant.APP_CUSTOMER_INFO);
//		if(customer != null){
//			String key = request.getHeader("key");
//			String token = request.getHeader("token");
//			
//			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(token)){
//				String account = RedisUtils.getValue(key);
//				if(StringUtils.isNotEmpty(account) && token.equals(TokenUtil.getIns().getToken(account))){
//					//验证通过
//					return true;
//				}
//			}
//			//登陆过的用户如果token不对就返回
//			valifyFail(request,response);
//            return false;
//		}
		//未登陆用户不检查，可以浏览商品
		//valifyFail(request,response);
		return true;
	}
	
	public void valifyFail(HttpServletRequest request, HttpServletResponse response){
		//logout 并且  跳转登陆页
		System.out.println("------------------------------拦截器验证失败!!!-----------------------------------");
		
	}
	
}
