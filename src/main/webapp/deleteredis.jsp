<%@page import="com.mfangsoft.zhuangjialong.common.utils.SysConstant"%>
<%@page import="org.springframework.data.redis.core.ListOperations"%>
<%@page import="org.springframework.data.redis.core.RedisTemplate"%>
<%@page import="com.mfangsoft.zhuangjialong.common.utils.RedisUtils"%>
<%@page import="com.mfangsoft.zhuangjialong.common.initcontext.InitContextBean"%>
<%@page import="com.mfangsoft.zhuangjialong.common.utils.SpringBeantUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%

RedisTemplate<String,Object> redisTemplate =RedisUtils.getRedisTemplate();
redisTemplate.delete(SysConstant.REG_PRE_HUNDRED);

%>

</body>
</html>