package com.mfangsoft.zhuangjialong.integration.department.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;
import com.mfangsoft.zhuangjialong.integration.department.mapper.DepartmentEntityMapper;
import com.mfangsoft.zhuangjialong.integration.department.model.DepartmentEntity;
import com.mfangsoft.zhuangjialong.integration.department.service.DepatmentService;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.role.mapper.RolePositionEntityMapper;
import com.mfangsoft.zhuangjialong.integration.role.model.RoleEntity;
@Service
public class DepatmentServiceImpl implements DepatmentService {

	@Autowired
	private DepartmentEntityMapper departmentEntityMapper;
	
	@Autowired
	private RolePositionEntityMapper rolePositionEntityMapper;

	@Override
	public Boolean addDepatment(DepartmentEntity departmentEntities)throws ServiceException {
			departmentEntities.setIs_position(1);
			
//			if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.ENTERPRISE.getIndex().intValue()) {
//
//				departmentEntities.setRole_id(UserContext.getCurrentUser().getId());
//			}
//			if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {
//
//				departmentEntities.setRole_id(SysConstant.PARTNER_ROLE);
//			}
//			if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {
//
//				departmentEntities.setRole_id(SysConstant.BRAND_ROLE);
//			}
//			if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.SHOP.getIndex().intValue()) {
//
//				departmentEntities.setRole_id(SysConstant.SHOP_ROLE);
//			}
//			
//			if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PLATFORM.getIndex().intValue()) {
//
//				departmentEntities.setRole_id(SysConstant.DEV_ROLE);
//			}
			departmentEntities.setRole_id(UserContext.getCurrentUser().getId());
			departmentEntityMapper.insertSelective(departmentEntities);
		
		
		return true;

	}

	@Override
	public Boolean modifyDepatment(DepartmentEntity departmentEntities) throws ServiceException{
		// TODO Auto-generated method stub
		
		//for (DepartmentEntity departmentEntity : departmentEntities) {
			departmentEntityMapper.updateByPrimaryKeySelective(departmentEntities);
		//}
		return true;
	}

	@Override
	public Boolean addPosition(DepartmentEntity entity) {
		// TODO Auto-generated method stub

		entity.setIs_position(2);
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.ENTERPRISE.getIndex().intValue()) {
//
//			entity.setRole_id(SysConstant.ENTERPRISE_ROLE);
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {
//
//			entity.setRole_id(SysConstant.PARTNER_ROLE);
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {
//
//			entity.setRole_id(SysConstant.BRAND_ROLE);
//		}
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.SHOP.getIndex().intValue()) {
//
//			entity.setRole_id(SysConstant.SHOP_ROLE);
//		}
//		
//		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PLATFORM.getIndex().intValue()) {
//
//			entity.setRole_id(SysConstant.DEV_ROLE);
//		}
		entity.setRole_id(UserContext.getCurrentUser().getId());
		if (departmentEntityMapper.insert(entity) > 0) {

			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean modifyPosition(DepartmentEntity entity) {
		// TODO Auto-generated method stub
		if (departmentEntityMapper.updateByPrimaryKeySelective(entity) > 0) {

			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Tree> queryDepOrPosition(DepartmentEntity entity) {
		// TODO Auto-generated method stub
		
		entity.setRole_id(UserContext.getCurrentUser().getId());
		
		
		
		List<DepartmentEntity> entities= new ArrayList<>();// departmentEntityMapper.selectAll(entity);
		
		List<Tree> list = new ArrayList<>();
		
		
		return this.getParent(list,entities);
	}
	
	private  List<Tree> getParent(List<Tree> list,List<DepartmentEntity> departmentEntities)
	{
		for (DepartmentEntity departmentEntity : departmentEntities) {
			if(departmentEntity.getParent_id()==null){
				Tree<Tree> tree = new Tree<>();
				tree.setText(departmentEntity.getName());
				tree.setId(departmentEntity.getId()+"");
				tree.setSprite("folder");
				tree.setItems(this.getChildren(departmentEntity.getId(), departmentEntities));
				list.add(tree);
			}
			
		}
		
		return list;
		
	}
	
	private List<Tree> getChildren(Long parentId,List<DepartmentEntity> departmentEntities){
		
		List<Tree>  trees  = new ArrayList<>();
		
		for (DepartmentEntity departmentEntity : departmentEntities) {
			
			if(departmentEntity.getParent_id()==null){
				
				continue;
			}
			if(departmentEntity.getParent_id().longValue()==parentId.longValue()){
				Tree<Tree> tree = new Tree<>();
				tree.setId(departmentEntity.getId()+"");
				tree.setText(departmentEntity.getName());
				tree.setSprite("folder");
				tree.setItems(this.getChildren(departmentEntity.getId(), departmentEntities));
				trees.add(tree);
			}
			
		}
		
		return trees;
	}

	@Override
	public List<DepartmentEntity> getDepForCombox() {
		// TODO Auto-generated method stub
		
		Long role_id=null;
		
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.ENTERPRISE.getIndex().intValue()) {

			role_id= new Long(UserType.ENTERPRISE.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {

			role_id=new Long(UserType.PARTNER.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {

			role_id=new Long(UserType.BRAND.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.SHOP.getIndex().intValue()) {

			role_id=new Long(UserType.SHOP.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PLATFORM.getIndex().intValue()) {

			role_id=new Long(UserType.PLATFORM.getIndex());
		}
		return departmentEntityMapper.getDepForCombox(new Long(1),role_id);
	}

	@Override
	public List<DepartmentEntity> getPositionForCombox(Long id) {
		// TODO Auto-generated method stub
		return departmentEntityMapper.getPositionForCombox(id);
	}

	@Override
	public List<DepartmentEntity> queryDepOrPositionList(DepartmentEntity entity) {
		// TODO Auto-generated method stub
		
		
		
		Map<String,Object> map= new HashMap<>();
		
		map.put("parent_id", entity.getParent_id());
		
		//map.put("role_id", UserContext.getCurrentUser().getId());
		
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.ENTERPRISE.getIndex().intValue()) {

			map.put("user_type", UserType.ENTERPRISE.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PARTNER.getIndex().intValue()) {

			map.put("user_type", UserType.PARTNER.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.BRAND.getIndex().intValue()) {

			map.put("user_type", UserType.BRAND.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.SHOP.getIndex().intValue()) {

			map.put("user_type", UserType.SHOP.getIndex());
		}
		if (UserContext.getCurrentUser().getUser_type().intValue() == UserType.PLATFORM.getIndex().intValue()) {

			map.put("user_type", UserType.PLATFORM.getIndex());
		}
		
		
		
//		entity.setRole_id(UserContext.getCurrentUser().getId());
		return departmentEntityMapper.selectAll(map);
	}

	@Override
	public Boolean removeDepatment(Long id)throws ServiceException {
		// TODO Auto-generated method stub
		
		//for (DepartmentEntity departmentEntity : entity) {
		
		DepartmentEntity departmentEntity=departmentEntityMapper.selectByPrimaryKey(id);
		
		if(departmentEntity.getIs_position().intValue()==1){
			
			List<DepartmentEntity> list= departmentEntityMapper.getPositionForCombox(id);
			
			if(list!=null&&list.size()>0){
				
				return false;
				
			}else{
				departmentEntityMapper.deleteByPrimaryKey(id);
			}
		}else{
			
			List<RoleEntity> roleEntities=rolePositionEntityMapper.selectRoleByPositionId(id);
			
			if(roleEntities!=null&&roleEntities.size()>0){
				return false;
			}else{
				
				departmentEntityMapper.deleteByPrimaryKey(id);
			}
			
		}
		//}
		return true;
	}

}
