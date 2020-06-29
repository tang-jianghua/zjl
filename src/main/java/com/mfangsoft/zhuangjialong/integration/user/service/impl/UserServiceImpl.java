package com.mfangsoft.zhuangjialong.integration.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserRoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
import com.mfangsoft.zhuangjialong.integration.user.service.EasemobIMService;
import com.mfangsoft.zhuangjialong.integration.user.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserEntityMapper mapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleEntityMapper;
	
	
	@Autowired
	private ShopEntityMapper  shopEntityMapper;
	
	@Autowired
	private EasemobIMService easemobIMService;
	
	
	@Override
	public Boolean addUser(UserEntity userEntity) throws Exception {
		userEntity.setPwd(MD5Util.MD5(userEntity.getPwd()));
		userEntity.setState(UserState.OPEN.getIndex());
		userEntity.setCreate_time(new Date());
		
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.ENTERPRISE.getIndex().intValue()) {

			
			userEntity.setUser_type(UserType.ENTERPRISE.getIndex().intValue());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {

			userEntity.setUser_type(UserType.PARTNER.getIndex().intValue());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {

			userEntity.setUser_type(UserType.BRAND.getIndex().intValue());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.SHOP.getIndex().intValue()) {

			userEntity.setUser_type(UserType.SHOP.getIndex().intValue());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PLATFORM.getIndex().intValue()) {

			userEntity.setUser_type(UserType.PLATFORM.getIndex().intValue());
		}
		if(mapper.insert(userEntity)>0)
		{
			
			UserRoleEntity roleEntity =userEntity.getRole();
			
			roleEntity.setUser_id(userEntity.getId());
			
			userRoleEntityMapper.insert(roleEntity);
			
			/*if(userEntity.getIs_seat()==1){
				
				easemobIMService.register(userEntity.getAccount());
			}*/
			
			return true;
		}else{
			
			return false;
		}
	
		// TODO Auto-generated method stub
	}


	@Override
	public Boolean closeUser(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		if(mapper.closeUser(id)>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Boolean openUser(Long id) throws Exception {
		
		if(mapper.openUser(id)>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Boolean modifyUser(UserEntity userEntity) throws Exception {
		// TODO Auto-generated method stub
		UserEntity entity=	mapper.selectByPrimaryKey(userEntity.getId());
	
		Boolean boolean1 = false; 
		
		if(entity!=null&&entity.getId()!=null){
			
			userEntity.setPwd(entity.getPwd());
			
			if(mapper.updateByPrimaryKey(userEntity)>0){
				
				boolean1=true;
			}else{
				
				boolean1=false;
			}
			
		}
	
	
		
		return boolean1;
	}


	@Override
	public Boolean modifyPassword(Long id, String oldPwd, String newPwd,ResponseMessage<String> message) throws Exception {
		// TODO Auto-generated method stub
		UserEntity entity =mapper.selectByPrimaryKey(id);
		Boolean boolean1 = false; 
		if(entity!=null&&entity.getId()!=null){
			
			if(MD5Util.MD5(oldPwd).equals(entity.getPwd())){
				
				Map<String,Object> map = new HashMap<>();
				
				map.put("id", id);
				
				map.put("new_pwd", MD5Util.MD5(newPwd));
				
				if(mapper.modifyPassword(map)>0){
					
					boolean1=true;
				}
				
			}else{
				message.setCode(SysConstant.FAILURE_CODE);
				message.setMessage("旧密码错误");
			}
		}
		
		
		return boolean1;
	}


	@Override
	public void getUserPage(Page<UserEntity> page) {
		// TODO Auto-generated method stub
		
		Map<String, Object> param = (Map<String, Object>) page.getParam();

//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.ENTERPRISE.getIndex().intValue()) {
//
//			param.put("role_id", UserContext.getCurrentUser().getUser_type());
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {
//
//			param.put("role_id", SysConstant.PARTNER_ROLE);
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {
//
//			param.put("role_id", SysConstant.BRAND_ROLE);
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.SHOP.getIndex().intValue()) {
//
//			param.put("role_id", SysConstant.SHOP_ROLE);
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PLATFORM.getIndex().intValue()) {
//
//			param.put("role_id", SysConstant.DEV_ROLE);
//		}
		
		param.put("role_id", UserContext.getCurrentUser().getId());
		page.setData(mapper.selectUserForPage(page));
		
	}


	@Override
	public UserEntity getUserById(Long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}


	@Override
	public UserEntity getUserByAccount(String account) {
		// TODO Auto-generated method stub
		
		
		
		return mapper.getUserByAccount(account);
	}


	@Override
	public Boolean resetpassword(Long id) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("id", id);
		map.put("pwd", MD5Util.MD5("888888"));
		
		if(mapper.resetpassword(map)>0){
			return true;
		}
		return false;
	}


	@Override
	public UserEntity selectUserByDep(Long id) {
		// TODO Auto-generated method stub
		
		ShopEntity  shopEntity=shopEntityMapper.selectByPrimaryKey(id);
		
		shopEntity.getSys_user_id();
		
		List<UserEntity> u=mapper.selectUserByDep(shopEntity.getSys_user_id());
		
		if(u!=null&&u.size()>0){
			
			
			Random random = new Random();
			
			int r=random.nextInt(u.size());
			
			return u.get(r);
		}
		
		return null;
	}

}
