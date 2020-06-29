package com.mfangsoft.zhuangjialong.integration.enterprise.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildEnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildMaterialsEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.EnterpriseEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildMaterialsEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.service.ClassifyService;
@Service
public class ClassifyServiceImpl  implements ClassifyService {

	@Autowired
	private BuildClassEntityMapper buildClassEntityMapper;
	
	@Autowired
	private BuildMaterialsEntityMapper buildMaterialsEntityMapper;
	
	@Autowired
	private BuildEnterpriseEntityMapper buildEnterpriseEntityMapper;
	
	
	@Autowired
	private EnterpriseEntityMapper enterpriseEntityMapper;
	
	
	@Autowired
	private BrandEntityMapper brandEntityMapper;
	
	@Override
	public Boolean addBuildClass(BuildClassEntity buildClassEntity) {
		// TODO Auto-generated method stub
		
		if(buildClassEntityMapper.insert(buildClassEntity)>0){
			
			return  true;
		}
		return false;
	}

	@Override
	public Boolean addBuildMaterials(BuildMaterialsEntity buildMaterialsEntity) {
		
		// TODO Auto-generated method stub
		
		if(buildMaterialsEntityMapper.insert(buildMaterialsEntity)>0)
		{
			return true;
		}
		return false;
	}

	@Override
	public Boolean addBuildEnterpise(BuildEnterpriseEntity buildEnterpriseEntity) {
		// TODO Auto-generated method stub
		if(buildEnterpriseEntityMapper.insert(buildEnterpriseEntity)>0)
		{
			return true;
		}
		return false;
		
	}

	@Override
	public Boolean modifyBuildClass(BuildClassEntity buildClassEntity) {
		// TODO Auto-generated method stub
		if(buildClassEntityMapper.updateByPrimaryKeySelective(buildClassEntity)>0)
		{
			return true;
		}
		return false;
	}

	@Override
	public Boolean modifyBuildMaterials(BuildMaterialsEntity buildMaterialsEntity) {
		// TODO Auto-generated method stub
		if(buildMaterialsEntityMapper.updateByPrimaryKey(buildMaterialsEntity)>0)
		{
			return true;
		}
		return false;
	}

	@Override
	public Boolean modifyBuildEnterpise(BuildEnterpriseEntity buildEnterpriseEntity) {
		// TODO Auto-generated method stub
		if(buildEnterpriseEntityMapper.updateByPrimaryKey(buildEnterpriseEntity)>0)
		{
			return true;
		}
		return false;
	}

	public  List<BuildEnterpriseEntity> getBuildEnterpriseList(){
		 
		List<BuildEnterpriseEntity>  buildEnterpriseEntities= buildEnterpriseEntityMapper.getBuildEnterpriseList();
		
		for (BuildEnterpriseEntity buildEnterpriseEntity : buildEnterpriseEntities) {
			buildEnterpriseEntity.setBuildClassEntity(buildClassEntityMapper.selectByPrimaryKey(buildEnterpriseEntity.getClass_id()));
		}
				
		
		 return buildEnterpriseEntities;
	 }
	 
	public  List<BuildMaterialsEntity>  getBuildMaterialsList(){
		  
		  return buildMaterialsEntityMapper.getBuildMaterialsList();
	  }
	  
	public  List<BuildClassEntity> geBuildClassEntities(){
		  return buildClassEntityMapper.geBuildClassEntities();
	  }

	@Override
	public Boolean removeBuildEnterpise(Long id) {
		// TODO Auto-generated method stub
		List<EnterpriseEntity>  enterpriseEntities=enterpriseEntityMapper.getEnterpriseByBuildId(id);
		
		List<BrandEntity>  brandEntities=brandEntityMapper.getEnterpriseByBuildId(id);
		
		if((enterpriseEntities!=null&&enterpriseEntities.size()==0)&&(brandEntities!=null&&brandEntities.size()==0)){
			
			buildEnterpriseEntityMapper.deleteByPrimaryKey(id);
			return true;
		}
		
		
		
		
		
		return false;
	}

	@Override
	public Boolean removeBuildClass(Long id) {
		// TODO Auto-generated method stub
		List<BuildEnterpriseEntity> buildEnterpriseEntities=buildEnterpriseEntityMapper.getBuildEnterpriseListByclassId(id);
		
		if(buildEnterpriseEntities!=null&&buildEnterpriseEntities.size()==0){
			
			
			buildClassEntityMapper.deleteByPrimaryKey(id);
			return true;
			
		}
		
		
		return false;
	}

}
