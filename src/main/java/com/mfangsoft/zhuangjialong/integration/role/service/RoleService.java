package com.mfangsoft.zhuangjialong.integration.role.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.RolePermissionEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.RolePositionEntity;
import com.mfangsoft.zhuangjialong.integration.role.model.UserRoleEntity;

public interface RoleService {

	
	Boolean createRole(Map<String,Object>  entities);
	
	Boolean  modifyRole(Map<String,Object>  roleEntities);
	
	
	Boolean removeRole(Long id);
	
	/**
	 * 更新权限到角色
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Boolean updatePermission2Role(Map<String,Object> map) throws Exception;
	
	/**
	 * 更新角色到岗位
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Boolean updateRole2Position(Map<String,Object> map) throws Exception;
	/**
	 * 更新用户到岗位
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Boolean updateUser2Position(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 更新用户权限
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Boolean updateUserPermission(Map<String,Object> map) throws Exception;
	
	
	
	
	List<RoleEntity> getRolePosition(Long id) throws Exception;
	
	
	Page<RoleEntity> getRoleListForPage(Page<RoleEntity> page) throws Exception;
	
	
	List<Long> getPermissionByRoleId(Long id);
	
	
	UserRoleEntity selectUserRoleByUserId(Long id);
	
	
	RoleEntity getRoleById(Long id);
	
	
	
}
