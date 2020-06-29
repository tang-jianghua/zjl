package com.mfangsoft.zhuangjialong.integration.newproduct.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.ClassAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.ClassAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.ClassAttrService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Service
public class ClassAttrServiceImpl implements ClassAttrService {

	@Autowired
	private ClassAttrValueEntityMapper classAttrValueEntityMapper;
	@Autowired
	private ClassAttrEntityMapper  classAttrEntityMapper;
	@Override
	public List<ClassAttrEntity> getClassAttrEntityByClassId() {
		
		UserEntity userEntity = UserContext.getCurrentUser();
		
		if(userEntity.getUser_type().intValue()==UserType.BRAND.getIndex().intValue()){
			
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			
			List<ClassAttrEntity> classAttrEntities= getClassAttrEntity(brandEntity.getBuildEnterpriseEntity().getClass_id()+"");
					
				
			if(classAttrEntities==null){
				
				return classAttrEntityMapper.getClassAttrEntityByClassId(brandEntity.getBuildEnterpriseEntity().getClass_id());
				
			}else{
				return classAttrEntities;
			}
		}else if(userEntity.getUser_type().intValue()==UserType.ENTERPRISE.getIndex().intValue()){
			
			EnterpriseEntity enterpriseEntity = (EnterpriseEntity) UserContext.getCurrentUserInfo();
			List<ClassAttrEntity> classAttrEntities= getClassAttrEntity(enterpriseEntity.getBuildEnterpriseEntity().getClass_id()+"") ;
			if(classAttrEntities==null){
				
				return classAttrEntityMapper.getClassAttrEntityByClassId(enterpriseEntity.getBuildEnterpriseEntity().getClass_id());
				
			}else{
				return classAttrEntities;
			}
			
			
		}
		
		
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private List<ClassAttrEntity> getClassAttrEntity(String class_id){
		
		
		try {
			return (List<ClassAttrEntity>) RedisUtils.getRedisTemplate().opsForHash().get("class_attr", class_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	

	@Override
	public List<ClassAttrValueEntity> getClassAttrValueEntityByAttrId(Long attr_id, Integer iswrite) {
		// TODO Auto-generated method stub
		List<ClassAttrValueEntity> classAttrValueEntities=getClassAttrValue(attr_id+"");
				
				
		
		if(ClassAttrValueEntity.iswrite.intValue()==iswrite.intValue()){
			
			if(classAttrValueEntities==null){
				
				return classAttrValueEntityMapper.getClassAttrValueEntityByAttrIdWrite(attr_id);
			}else{
				
				 return classAttrValueEntities;
			}
			
		
			
		}else{
			if(classAttrValueEntities==null){
				
				return  classAttrValueEntityMapper.getClassAttrValueEntityByAttrIdNoWrite(attr_id);
			}else{
				
				 return classAttrValueEntities;
			}
			
		}
		
	}

	private List<ClassAttrValueEntity> getClassAttrValue(String id){
		
		try {
			return (List<ClassAttrValueEntity>) RedisUtils.getRedisTemplate().opsForHash().get("class_attr_value", id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@Override
	public List<ClassAttrValueEntity> getClassAttrValueEntityByParentId(Map<String,Long> map) {
		// TODO Auto-generated method stub
		return classAttrValueEntityMapper.getClassAttrValueEntityByParent(map);
	}


	@Override
	public List<ClassAttrValueEntity> getClassAttrValueEntityforRedisByAttrId(Long attr_id, Integer iswrite) {
		// TODO Auto-generated method stub
		
		if(ClassAttrValueEntity.iswrite.intValue()==iswrite.intValue()){
			
			
				
				return classAttrValueEntityMapper.getClassAttrValueEntityByAttrIdWrite(attr_id);
			
		
			
		}else{
			
				
				return  classAttrValueEntityMapper.getClassAttrValueEntityByAttrIdNoWrite(attr_id);
			
			
		}
		
	}

	
}
