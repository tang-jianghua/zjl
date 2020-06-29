package com.mfangsoft.zhuangjialong.integration.enterprise.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildMaterialsEntity;

public interface ClassifyService {
	
	Boolean addBuildClass(BuildClassEntity buildClassEntity);
	
	Boolean addBuildMaterials(BuildMaterialsEntity buildMaterialsEntity);


	Boolean addBuildEnterpise(BuildEnterpriseEntity buildEnterpriseEntity);
	
	
	Boolean modifyBuildClass(BuildClassEntity buildClassEntity);
	

	Boolean modifyBuildMaterials( BuildMaterialsEntity buildMaterialsEntity);

	
	Boolean modifyBuildEnterpise( BuildEnterpriseEntity buildEnterpriseEntity);
	
	
	Boolean removeBuildEnterpise(Long id);
	
	
	Boolean removeBuildClass(Long id);
	
	
	
	  List<BuildEnterpriseEntity> getBuildEnterpriseList();
	  List<BuildMaterialsEntity>  getBuildMaterialsList();
	  List<BuildClassEntity> geBuildClassEntities();
}
