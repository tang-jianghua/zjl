package com.mfangsoft.zhuangjialong.common.utils;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class SpringBeantUtils<T> {
	public static ApplicationContext applicationContext;

	//private static RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");;
	//private static ListOperations<String, String> listOps = redisTemplate.opsForList();
	
	public static void setApplicationContext(ApplicationContext applicationContext) {
	 
		SpringBeantUtils.applicationContext=applicationContext;
	}

	public static Object getBean(String name) {
	  return applicationContext.getBean(name);
	}
	
	public T getBeanType(Class<T> org) {
	 return applicationContext.getBean(org);
	}
}
