package com.mfangsoft.zhuangjialong.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisUtils {
	
	
	private static RedisTemplate redisTemplate = (RedisTemplate)SpringBeantUtils.getBean("redisTemplate");
	private static ListOperations<String, String> listOps = redisTemplate.opsForList();
	private static ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
	private static HashOperations<String,String,Object> hashOperations = redisTemplate.opsForHash();
	
//	private static Jackson2JsonRedisSerializer<T>  jackson2JsonRedisSerializer ;
	
	public static RedisTemplate getRedisTemplate(){
		
		return redisTemplate;
	}
	
	public static String lpop(String value) {
		return listOps.leftPop(value);
	}

	public static void lpush(String key, String value) {
		listOps.leftPush(key, value);
	}

	public static void lputList(String key, Collection<String> listValue) {
		listOps.leftPushAll(key, listValue);
	}
	
	public static String getValue(String key){
		return valueOps.get(key);
	}
	public static String setAndGet(String key, String value){
		return valueOps.getAndSet(key, value);
	}
	
	public static void setValue(String key ,String value){
		valueOps.set(key, value);
	}
	
	public static void setWithTimeLimit(String key ,String value, Long timeOutMin){
		valueOps.set(key, value, timeOutMin, TimeUnit.SECONDS);
	}
	
	public static void delValue(String key){
		redisTemplate.delete(key);
	}
	
	//////////////////////map///////////////////
	
	public static Map<String,Object> getMap(String key){
		return hashOperations.entries(key);
	}
	
	public static void setMap(String key, Map<String,Object> map){
		hashOperations.putAll(key, map);
	}
	
	public static void delMap(String key, String... hashKeys){
		hashOperations.delete(key, hashKeys);
	}
	
	public static boolean hasMap(String key, String hashKey){
		return hashOperations.hasKey(key, hashKey);
	}
	
	public static Object getMapValue(String key,String hashKey){
		if(hashOperations.hasKey(key, hashKey)){
			return hashOperations.get(key, hashKey);
		}else{
			return null;
		}
	}
	
	public static void setMapValue(String key,String hashKey,Object value){
		if(hashOperations.entries(key) != null){
			hashOperations.put(key, hashKey, value);
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(hashKey, value);
			hashOperations.putAll(hashKey, map);
		}
	}
}
