package com.mfangsoft.zhuangjialong.integration.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.MD5Util;
import com.mfangsoft.zhuangjialong.integration.bank.mapper.BankEntityMapper;
import com.mfangsoft.zhuangjialong.integration.commerce.mapper.CommerceEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseInfoEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseOneEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseTwoEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BaseEnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseInfoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseOneEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.EnterpriseService;
import com.mfangsoft.zhuangjialong.integration.enums.UserState;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.permission.mapper.PermissionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserRoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class EnterpriseServiceimpl implements EnterpriseService {
	
	
	
	private final static Long ENTERPRISE_ROLE=71L;

	@Autowired
	private EnterpriseEntityMapper enterpriseEntityMapper;
	
	@Autowired
	private EnterpriseInfoEntityMapper enterpriseInfoEntityMapper;
	
	
	@Autowired
	private BankEntityMapper bankEntityMapper;
	
	@Autowired
	private CommerceEntityMapper commerceEntityMapper;
	
	
	@Autowired
	private UserEntityMapper userEntityMapper;
	@Autowired
	private BuildClassEntityMapper buildClassEntityMapper;
	
	
	@Autowired 
	private EnterpriseOneEntityMapper enterpriseOneEntityMapper;
	
	
	@Autowired
	private EnterpriseTwoEntityMapper enterpriseTwoEntityMapper;
	
	@Autowired
	private UserRoleEntityMapper  userRoleEntityMapper;
	
	@Autowired
	private PermissionEntityMapper permissionEntityMapper;
	
	@Autowired
	private  BuildEnterpriseEntityMapper buildEnterpriseEntityMapper;
	
	@Override
	public Boolean addEnterprise(EnterpriseEntity enterpriseEntity) {
		// TODO Auto-generated method stub
		
		
		
		enterpriseEntity.getClass_id();
		bankEntityMapper.insert(enterpriseEntity.getBankEntity());
		
		commerceEntityMapper.insert(enterpriseEntity.getCommerceEntity());
		
		UserEntity userEntity = createEnterpriseUser(enterpriseEntity);
		
		createEnterpriseUserRole(userEntity,enterpriseEntity);
		
		setEnerpriseEntity(enterpriseEntity, userEntity);
	
		if(enterpriseEntityMapper.insertSelective(enterpriseEntity)>0){
			
			return true;
		}
		return false;
	}


	private void setEnerpriseEntity(EnterpriseEntity enterpriseEntity, UserEntity userEntity) {
		enterpriseEntity.setSys_user_id(userEntity.getId());
		
		enterpriseEntity.setBank_info_id(enterpriseEntity.getBankEntity().getId());
		
		enterpriseEntity.setCommerce_id(enterpriseEntity.getCommerceEntity().getId());
		
		enterpriseEntity.setCreate_time(new Date());
		
		enterpriseEntity.setCreater(UserContext.getCurrentUserId());
	}


	private void createEnterpriseUserRole(UserEntity userEntity,EnterpriseEntity enterpriseEntity) {
		
		UserRoleEntity  roleEntity = new UserRoleEntity();
		
		//roleEntity.setRole_id(new Long(SysConstant.entmap.get(enterpriseEntity.getClass_id()+"").toString()));
		roleEntity.setRole_id(ENTERPRISE_ROLE);
		
		roleEntity.setUser_id(userEntity.getId());
		
		userRoleEntityMapper.insertSelective(roleEntity);
	}


	private UserEntity createEnterpriseUser(EnterpriseEntity enterpriseEntity) {
		UserEntity userEntity=enterpriseEntity.getUserEntity();
		
		userEntity.setPhone_num(enterpriseEntity.getPhone_num());
		
		userEntity.setCreate_time(new Date());
		
		userEntity.setPwd(MD5Util.MD5(userEntity.getPwd()));
		
		userEntity.setState(UserState.OPEN.getIndex());
		
		userEntity.setUser_type(UserType.ENTERPRISE.getIndex());
		
		userEntity.setUser(enterpriseEntity.getPrincipal());
		
		userEntityMapper.insertSelective(userEntity);
		return userEntity;
	}

	@Override
	public EnterpriseEntity getEnterpriseById(Long id) {
		// TODO Auto-generated method stub
		EnterpriseEntity enterpriseEntity=enterpriseEntityMapper.selectByPrimaryKey(id);
		BuildEnterpriseEntity buildEnterpriseEntity =buildEnterpriseEntityMapper.selectByPrimaryKey(enterpriseEntity.getBuild_enterpise());
		
		enterpriseEntity.setBuildEnterpriseEntity(buildEnterpriseEntity);
		
		enterpriseEntity.setBuildClassEntity(buildClassEntityMapper.selectByPrimaryKey(enterpriseEntity.getClass_id()));
		
		
		return enterpriseEntity;
	}

	@Override
	public Boolean modifyEnterprise(EnterpriseEntity enterpriseEntity) {
		// TODO Auto-generated method stub
		EnterpriseEntity entity=enterpriseEntityMapper.selectByPrimaryKey(enterpriseEntity.getId());
		
		enterpriseEntity.getBankEntity().setId(entity.getBank_info_id());
		
		enterpriseEntity.getCommerceEntity().setId(entity.getCommerce_id());
		
		
		bankEntityMapper.updateByPrimaryKeySelective(enterpriseEntity.getBankEntity());
		
		commerceEntityMapper.updateByPrimaryKeySelective(enterpriseEntity.getCommerceEntity());
		
		
		
		
		
		
		
		
		
		UserEntity user=userEntityMapper.selectByPrimaryKey(entity.getSys_user_id());
		//permissionEntityMapper.deletePermissionByRoleId(user.getId());
		//this.createEnterpriseUserRole(user, enterpriseEntity);
		
		user.setUser(enterpriseEntity.getPrincipal());
		
		user.setPhone_num(enterpriseEntity.getPhone_num());
		
		
		user.setAccount(enterpriseEntity.getUserEntity().getAccount());
		
		userEntityMapper.updateByPrimaryKeySelective(user);
		
		
		enterpriseEntity.setUpdate_time(new Date());
		
		enterpriseEntity.setUpdater(UserContext.getCurrentUserId());
		
		if(enterpriseEntityMapper.updateByPrimaryKeySelective(enterpriseEntity)>0){
			
			return true;
		}
		return false;
	}

	@Override
	public Page<Map<String,Object>> getEnterpriseForPage(Page<Map<String,Object>> page) {
		// TODO Auto-generated method stub
		page.setData(enterpriseEntityMapper.getEnterpriseForPage(page));
		return page;
	}

	@Override
	public Boolean addEnterpriseInfo(EnterpriseInfoEntity enterpriseInfoEntity) {
		// TODO Auto-generated method stub
		
		enterpriseInfoEntity.setEnterprise_id(((EnterpriseEntity)UserContext.getCurrentUserInfo()).getId());
		if(enterpriseInfoEntityMapper.insertSelective(enterpriseInfoEntity)>0){
			return true;
			
		}
		return false;
	}

	@Override
	public EnterpriseInfoEntity getEnterpriseInfoById(Long id) {
		// TODO Auto-generated method stub
		return enterpriseInfoEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean modifyEnterpriseInfo(EnterpriseInfoEntity enterpriseInfoEntity) {
		// TODO Auto-generated method stub
		if(enterpriseInfoEntityMapper.updateByPrimaryKeySelective(enterpriseInfoEntity)>0){
			return true;
			
		}
		return false;
	}

	@Override
	public Boolean closeOrOpenEnterprise(Long id, Integer state) {
		// TODO Auto-generated method stub

		EnterpriseEntity enterpriseEntity = enterpriseEntityMapper.selectByPrimaryKey(id);

		UserEntity userEntity = enterpriseEntity.getUserEntity();

		userEntity.setState(state);

		if(userEntityMapper.updateByPrimaryKeySelective(userEntity)>0){
			
			return true;
		}
		return false;
	}

	@Override
	public List<BuildClassEntity> getBuildClassEntities() {
		// TODO Auto-generated method stub
		
		
		
		return buildClassEntityMapper.geBuildClassEntities();
	}

	@Override
	public List<Map<String, Object>> getEnterpriseInfoList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return enterpriseOneEntityMapper.getEnterpriseOneByEntIdList(map);
	}

	@Override
	public Boolean addEnterpriseInfoOne(EnterpriseOneEntity enterpriseOneEntity) {
		// TODO Auto-generated method stub
		enterpriseOneEntity.setEnterprise_id(((EnterpriseEntity)UserContext.getCurrentUserInfo()).getId());
		if(enterpriseOneEntityMapper.insertSelective(enterpriseOneEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean addEnterpriseInfoTwo(List<EnterpriseTwoEntity> enterpriseTwoEntity) {
		// TODO Auto-generated method stub
		
		for (EnterpriseTwoEntity enterpriseTwoEntity2 : enterpriseTwoEntity) {
			enterpriseTwoEntityMapper.insertSelective(enterpriseTwoEntity2);
		}
		
		
		return true;
	}

	@Override
	public Boolean modifyEnterpriseInfoOne(EnterpriseOneEntity enterpriseOneEntity) {
		// TODO Auto-generated method stub
		enterpriseOneEntity.setEnterprise_id(((EnterpriseEntity)UserContext.getCurrentUserInfo()).getId());
		if(enterpriseOneEntityMapper.updateByPrimaryKeySelective(enterpriseOneEntity)>0){
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean modifyEnterpriseInfoTwo(EnterpriseTwoEntity enterpriseTwoEntity) {
		// TODO Auto-generated method stub
		if(enterpriseTwoEntityMapper.updateByPrimaryKeySelective(enterpriseTwoEntity)>0){
			return true;
		}
		return false;
	}

	@Override
	public List<EnterpriseEntity> getEnterpriseBrandName() {
		// TODO Auto-generated method stub
		return enterpriseEntityMapper.getEnterpriseBrandName();
	}


	@Override
	public EnterpriseEntity getEnterpriseEntity(Long userId) {
		// TODO Auto-generated method stub
		return enterpriseEntityMapper.getEnterpriseEntity(userId);
	}


	@Override
	public List<EnterpriseEntity> getEnterpriseName() {
		// TODO Auto-generated method stub
		return enterpriseEntityMapper.getEnterpriseBrandName();
	}


	@Override
	public EnterpriseInfoEntity getEnterpriseProfilesByUserId(Integer type) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", UserContext.getCurrentUserId());
		map.put("type", type);
		return enterpriseInfoEntityMapper.getEnterpriseProfilesByUserId(map);
	}


	@Override
	public List<EnterpriseInfoEntity> getEnterpriseDevelopmentByUserId(Integer type) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userId", UserContext.getCurrentUserId());
		map.put("type", type);
		
		return enterpriseInfoEntityMapper.getEnterpriseDevelopmentByUserId(map);
	}


	@Override
	public BaseEnterpriseTwoEntity getEnterpriseInfoTwo(Long id) {
		// TODO Auto-generated method stub
		return enterpriseTwoEntityMapper.selectByPrimaryKey(id);
	}


	@Override
	public EnterpriseOneEntity getEnterpriseInfoOne(Long id) {
		// TODO Auto-generated method stub
		return enterpriseOneEntityMapper.selectByPrimaryKey(id);
	}


	@Override
	public Boolean modifyBackEnterprise(EnterpriseEntity enterpriseEntity) {
		// TODO Auto-generated method stub
		if(enterpriseEntityMapper.updateByPrimaryKeySelective(enterpriseEntity)>0){
			
			
			
			EnterpriseEntity entity =enterpriseEntityMapper.selectByPrimaryKey(enterpriseEntity.getId());
			
			UserEntity userEntity=userEntityMapper.selectByPrimaryKey(entity.getSys_user_id());
			
			UserEntity userEntityUpdate = new UserEntity();
			
			userEntityUpdate.setUser(enterpriseEntity.getPrincipal());
			userEntityUpdate.setId(userEntity.getId());
			userEntityMapper.updateByPrimaryKeySelective(userEntityUpdate);
			return true;
		}
		return false;
	}


	@Override
	public List<Map<String, Object>> getEnterpriseInfoTwoByOneId(Long id) {
		// TODO Auto-generated method stub
		
		
		
		
		return enterpriseTwoEntityMapper.selectEnterpriseTwoByOne(id);
		
	}


	@Override
	public Boolean removeEnterpriseInfoOne(Long id) {
		// TODO Auto-generated method stub
		enterpriseOneEntityMapper.deleteByPrimaryKey(id);
		if(enterpriseTwoEntityMapper.removeEnterpriseTwoByOneId(id)>0){
			
			return true;
			
		}else {
			
			return false;
		}
	}


	@Override
	public Boolean removeEnterpriseInfoTwo(List<Long> ids) {
		// TODO Auto-generated method stub
		
		for (Long id : ids) {
			
			enterpriseTwoEntityMapper.deleteByPrimaryKey(id);
		}
		
		return true;
	}


	
	

}
