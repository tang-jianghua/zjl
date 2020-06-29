package com.mfangsoft.zhuangjialong.integration.user.service;

import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

public interface UserService {
	
	/**
	 * 创建用户
	 * @param userEntity
	 * @return
	 * @throws Exception
	 */
	Boolean  addUser(UserEntity userEntity) throws  Exception;
	
	
	/**
	 * 停用用户
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	Boolean  closeUser(Long id) throws Exception;
	
	/**
	 * 开启用户
	 * @param id 主键ID
	 * @return
	 * @throws Exception
	 */
	Boolean  openUser(Long id) throws Exception;
	
	
	
	/**
	 * 修改用户
	 * @param userEntity
	 * @return
	 * @throws Exception
	 */
	Boolean  modifyUser(UserEntity userEntity) throws Exception;
	
	
	Boolean modifyPassword(Long id,String oldPwd,String newPwd,ResponseMessage<String> message)throws Exception;
	
	
	void getUserPage(Page<UserEntity> page);
	
	
	UserEntity getUserById(Long id);
	
	
	UserEntity  getUserByAccount(String account);
	
	
	
	Boolean resetpassword(Long id);
	
	
	UserEntity selectUserByDep(Long id);
	
	
	
}
