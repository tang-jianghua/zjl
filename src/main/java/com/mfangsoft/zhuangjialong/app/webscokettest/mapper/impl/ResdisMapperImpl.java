package com.mfangsoft.zhuangjialong.app.webscokettest.mapper.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.mfangsoft.zhuangjialong.app.webscokettest.mapper.IRedisMapper;

@Repository
public class ResdisMapperImpl implements IRedisMapper {
	
	@Autowired
	private RedisTemplate<String, Object>  redisTemplate;
	
	@Resource(name="redisTemplate")
	private ListOperations<String, String> listOps;

	@Override
	public void sendMessage(String channel, Serializable message) {
		// TODO Auto-generated method stub
		redisTemplate.convertAndSend(channel, message);
		
	
	}

	@Override
	public void pushMessage(String key, String message) {
		// TODO Auto-generated method stub
		listOps.leftPush(key, message);
	}

	@Override
	public String popMessage(String key) {
		// TODO Auto-generated method stub
		return listOps.leftPop(key);
	}

}
