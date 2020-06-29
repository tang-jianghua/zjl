package com.mfangsoft.zhuangjialong.integration.permission.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;

public interface PermissionService {
	
	
	Boolean createPermission(PermissionEntity entity);
	
	Boolean modifyPermission(PermissionEntity entity);
	
	Boolean removePermission(Long id);
	
	
	List<Tree>  getPermission(); 
	
	List<PermissionEntity>  getPermissionByUserId(Long userId);
	
	
	List<Map<String,Object>>  getPermissionForZTree(); 

}
