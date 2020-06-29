package com.mfangsoft.zhuangjialong.integration.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;
import com.mfangsoft.zhuangjialong.integration.role.mapper.RoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.RolePermissionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.RolePositionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserPermissionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserPositionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.mapper.UserRoleEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.RolePermissionEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.RolePositionEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.UserPermissionEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.UserPositionEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.service.RoleService;
@Service
public class RoleServiecImpl implements RoleService{

	@Autowired
	private RoleEntityMapper entityMapper;
	@Autowired
	private RolePermissionEntityMapper permissionEntityMapper;
	
	@Autowired
	private RolePositionEntityMapper positionEntityMapper;
	
	@Autowired
	private UserPositionEntityMapper userPositionEntityMapper;
	
	@Autowired
	private UserPermissionEntityMapper userPermissionEntityMapper;
	
	
	@Autowired
	private RolePositionEntityMapper rolePositionEntityMapper;
	
	@Autowired
	private UserRoleEntityMapper userRoleEntityMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiecImpl.class);
	
	@Override
	public Boolean createRole(Map<String,Object>  entities) throws ServiceException {
		// TODO Auto-generated method stub
		
		
		Map<String,Object> roleMap=  (Map<String, Object>) entities.get("roles");
		//for (RoleEntity roleEntity : roleEntities) {
		
		    RoleEntity roleEntity = new RoleEntity();
		    roleEntity.setRole_name(roleMap.get("role_name").toString());
			entityMapper.insertSelective(roleEntity);
			RolePositionEntity record = new RolePositionEntity();
			record.setPosition_id(new Long(entities.get("position_id").toString()));
			record.setRole_id(roleEntity.getId());
			rolePositionEntityMapper.insertSelective(record);
		//}
		
		
		return true;
		
		
	}

	@Override
	public Boolean modifyRole(Map<String,Object> roleEntities) throws ServiceException{
		// TODO Auto-generated method stub
		 RoleEntity roleEntity = new RoleEntity();
		 Map<String,Object> roleMap=(Map<String, Object>) roleEntities.get("roles");
		 roleEntity.setId(new Long(roleEntities.get("id").toString()));
		 roleEntity.setRole_name(roleMap.get("role_name").toString());
			entityMapper.updateByPrimaryKeySelective(roleEntity);
		return true;
	}

	@Override
	public Boolean updatePermission2Role(Map<String,Object> map) throws Exception {
		// TODO Auto-generated method stub
		
		
		if(map.get("role_id")==null){
			
			LOGGER.info("role_id  is  null" );
			
			throw  new Exception("role_id  is  null");
			
		}
		
			Long roleId= new Long(map.get("role_id").toString());
			List<Long> list = permissionEntityMapper.selectPermissionByRoleId(roleId);
			List<String> peList = (List<String>) map.get("permission");
			List<String> srcList1 = getSrcList(peList);
			List<String> targetList1 = getSrcList(peList);
			List<String> strList2 =getTargetList(list);
			List<String> targetList2 = getTargetList(list);
			
			srcList1.removeAll(targetList2);
			// 插入数据库
			for (int i = 0; i < srcList1.size(); i++) {
				RolePermissionEntity entity = new RolePermissionEntity();
				entity.setRole_id(roleId);
				entity.setPermission_id(new Long(srcList1.get(i)));
				permissionEntityMapper.insert(entity);
			}
			strList2.removeAll(targetList1);
			// 删除数据库
			for (int i = 0; i < strList2.size(); i++) {
				Map<String, Long> deletePrame = new HashMap<String, Long>();
				deletePrame.put("role_id", roleId);
				deletePrame.put("permission_id", new Long(strList2.get(i)));
				permissionEntityMapper.deleteRolePermission(deletePrame);
			}
			return true;
	}
	
	private List<String>  getSrcList(List<String> list){
		List<String> srcList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {

			srcList.add(i, list.get(i));
		}
		return srcList;
	}
	
	private List<String>  getTargetList(List<Long> list){
		List<String> srcList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			srcList.add(i, list.get(i) + "");

		}
		return srcList;
	}

	@Override
	public Boolean updateRole2Position(Map<String, Object> map) throws Exception {
		if(map.get("position_id")==null){
			
			LOGGER.info("position_id  is  null" );
			
			throw  new Exception("position_id  is  null");
			
		}
		
			Long positionId= new Long(map.get("position_id").toString());
			List<Long> list = positionEntityMapper.selectPositionByRoleId(positionId);
			List<String> peList = (List<String>) map.get("role");
			List<String> srcList1 = getSrcList(peList);
			List<String> targetList1 = getSrcList(peList);
			List<String> strList2 =getTargetList(list);
			List<String> targetList2 = getTargetList(list);
			
			srcList1.removeAll(targetList2);
			// 插入数据库
			for (int i = 0; i < srcList1.size(); i++) {
				RolePositionEntity entity = new RolePositionEntity();
				entity.setPosition_id(positionId);
				entity.setRole_id(new Long(srcList1.get(i)));
				positionEntityMapper.insert(entity);
			}
			strList2.removeAll(targetList1);
			// 删除数据库
			for (int i = 0; i < strList2.size(); i++) {
				Map<String, Long> deletePrame = new HashMap<String, Long>();
				deletePrame.put("permission_id", positionId);
				deletePrame.put("role_id", new Long(strList2.get(i)));

				positionEntityMapper.deleteRolePosition(deletePrame);
			}
			return true;
	}

	@Override
	public Boolean updateUser2Position(Map<String, Object> map) throws Exception {
		if(map.get("position_id")==null){
			
			LOGGER.info("position_id  is  null" );
			
			throw  new Exception("position_id  is  null");
			
		}
		
			Long positionId= new Long(map.get("position_id").toString());
			List<Long> list = userPositionEntityMapper.selectUserByPositionId(positionId);
			List<String> peList = (List<String>) map.get("user");
			List<String> srcList1 = getSrcList(peList);
			List<String> targetList1 = getSrcList(peList);
			List<String> strList2 =getTargetList(list);
			List<String> targetList2 = getTargetList(list);
			
			srcList1.removeAll(targetList2);
			// 插入数据库
			for (int i = 0; i < srcList1.size(); i++) {
				UserPositionEntity entity = new UserPositionEntity();
				entity.setPosition_id(positionId);
				entity.setUser_id(new Long(srcList1.get(i)));
				userPositionEntityMapper.insert(entity);
			}
			strList2.removeAll(targetList1);
			// 删除数据库
			for (int i = 0; i < strList2.size(); i++) {
				Map<String, Long> deletePrame = new HashMap<String, Long>();
				deletePrame.put("position_id", positionId);
				deletePrame.put("user_id", new Long(strList2.get(i)));

				userPositionEntityMapper.deleteUserPosition(deletePrame);
			}
			return true;
	}

	@Override
	public Boolean updateUserPermission(Map<String, Object> map) throws Exception {
		
		
if(map.get("user_id")==null){
			
			LOGGER.info("user_id  is  null" );
			
			throw  new Exception("user_id  is  null");
			
		}
		
			Long userId= new Long(map.get("user_id").toString());
			List<Long> list = userPermissionEntityMapper.selectPermissionByUserId(userId);
			List<String> peList = (List<String>) map.get("permission");
			List<String> srcList1 = getSrcList(peList);
			List<String> targetList1 = getSrcList(peList);
			List<String> strList2 =getTargetList(list);
			List<String> targetList2 = getTargetList(list);
			
			srcList1.removeAll(targetList2);
			// 插入数据库
			for (int i = 0; i < srcList1.size(); i++) {
				UserPermissionEntity entity = new UserPermissionEntity();
				entity.setUser_id(userId);
				entity.setPermission_id(new Long(srcList1.get(i)));
				userPermissionEntityMapper.insert(entity);
			}
			strList2.removeAll(targetList1);
			// 删除数据库
			for (int i = 0; i < strList2.size(); i++) {
				Map<String, Long> deletePrame = new HashMap<String, Long>();
				deletePrame.put("user_id", userId);
				deletePrame.put("permission_id", new Long(strList2.get(i)));

				userPermissionEntityMapper.deleteUserPermission(deletePrame);
			}
			return true;
	}

	@Override
	public List<RoleEntity> getRolePosition(Long id) throws Exception {
		// TODO Auto-generated method stub
		return rolePositionEntityMapper.selectRoleByPositionId(id);
	}

	@Override
	public Page<RoleEntity> getRoleListForPage(Page<RoleEntity> page) throws Exception {
		// TODO Auto-generated method stub
		
		page.setData(entityMapper.getRoleListForPage(page));
		return page;
	}

	@Override
	public Boolean removeRole(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		
		UserRoleEntity userRoleEntity=userRoleEntityMapper.selectUserRoleByRoleId(id);
		
		if(userRoleEntity==null){
			entityMapper.deleteByPrimaryKey(id);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Long> getPermissionByRoleId(Long id) {
		// TODO Auto-generated method stub
		return permissionEntityMapper.selectPermissionByRoleId(id);
	}

	@Override
	public UserRoleEntity selectUserRoleByUserId(Long id) {
		// TODO Auto-generated method stub
		return userRoleEntityMapper.selectUserRoleByUserId(id);
	}

	@Override
	public RoleEntity getRoleById(Long id) {
		// TODO Auto-generated method stub
		return entityMapper.selectByPrimaryKey(id);
	}
}
