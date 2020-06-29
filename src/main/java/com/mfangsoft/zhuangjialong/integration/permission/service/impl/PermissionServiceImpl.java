package com.mfangsoft.zhuangjialong.integration.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.department.model.DepartmentEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.permission.mapper.PermissionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.permission.model.PermissionEntity;
import com.mfangsoft.zhuangjialong.integration.permission.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionEntityMapper entityMapper;
	
	
	private final static Long ENTERPRISE_ROLE=71L;
	@Override
	public Boolean createPermission(PermissionEntity entity) {
		// TODO Auto-generated method stub
		
		if(entityMapper.insertSelective(entity)>0){
			
			return true;
		}else
		{
			
			return false;
		}
		
	}

	@Override
	public Boolean modifyPermission(PermissionEntity entity) {
		// TODO Auto-generated method stub
		if(entityMapper.updateByPrimaryKey(entity)>0){
			
			return true;
		}else
		{
			
			return false;
		}
	}

	@Override
	public Boolean removePermission(Long id) {
		// TODO Auto-generated method stub
		if(entityMapper.deleteByPrimaryKey(id)>0){
			
			return true;
		}else
		{
			
			return false;
		}
	}

	@Override
	public List<Tree> getPermission() {
		// TODO Auto-generated method stub
		
		List<PermissionEntity> permissionEntities =UserContext.getCurrentPermission();
		//List<PermissionEntity> permissionEntities=entityMapper.selectAll();
		List<Tree> list = new ArrayList<>();
		return this.getParent(list, permissionEntities);
	}

	
	private  List<Tree> getParent(List<Tree> list,List<PermissionEntity> permissionEntities)
	{
		for (PermissionEntity permissionEntity : permissionEntities) {
			if(permissionEntity.getParent_id()==null){
				Tree<Tree> tree = new Tree<>();
				tree.setText(permissionEntity.getName());
				tree.setId(permissionEntity.getId()+"");
				tree.setSprite("folder");
				tree.setExpanded(true);
				tree.setItems(this.getChildren(permissionEntity.getId(), permissionEntities));
				list.add(tree);
			}
			
		}
		
		return list;
		
	}
	
	private List<Tree> getChildren(Long parentId,List<PermissionEntity> permissionEntities){
		
		List<Tree>  trees  = new ArrayList<>();
		for (PermissionEntity permissionEntity : permissionEntities) {
			
			if(permissionEntity.getParent_id()==null){
				
				continue;
			}
			if(permissionEntity.getParent_id().longValue()==parentId.longValue()){
				Tree<Tree> tree = new Tree<>();
				tree.setId(permissionEntity.getId()+"");
				tree.setText(permissionEntity.getName());
				tree.setSprite("folder");
				tree.setExpanded(true);
				tree.setItems(this.getChildren(permissionEntity.getId(), permissionEntities));
				trees.add(tree);
			}
			
		}
		
		return trees;
	}

	@Override
	public List<PermissionEntity> getPermissionByUserId(Long userId) {
		// TODO Auto-generated method stub
		
		
		return entityMapper.getPermissionByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getPermissionForZTree() {
		// TODO Auto-generated method stub
		List<Map<String, Object>>  list = new ArrayList<>();
		List<PermissionEntity> permissionEntities =UserContext.getCurrentPermission();
		
		for (PermissionEntity permissionEntity : permissionEntities) {
			
			
			Map<String, Object> map= new HashMap<>();
			map.put("name", permissionEntity.getName());
			map.put("pId", permissionEntity.getParent_id());
			map.put("id", permissionEntity.getId());
			list.add(map);
		}
		
		
		
		
		
		return list;
	}
}
