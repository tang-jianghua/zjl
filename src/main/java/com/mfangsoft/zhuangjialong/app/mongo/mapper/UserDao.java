package com.mfangsoft.zhuangjialong.app.mongo.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.mongo.model.UserEntity;

public interface UserDao {
	
	
	public abstract void _test();  
	  
    public abstract void createCollection();  
  
    public abstract List<UserEntity> findList(int skip, int limit);  
  
    public abstract List<UserEntity> findListByAge(int age);  
  
    public abstract UserEntity findOne(String id);  
  
    public abstract UserEntity findOneByUsername(String username);  
  
    public abstract void insert(UserEntity entity);  
  
    public abstract void update(UserEntity entity); 
    public abstract void deleteOne(String id);
}
