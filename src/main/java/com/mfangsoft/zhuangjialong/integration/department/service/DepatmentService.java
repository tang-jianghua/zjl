package com.mfangsoft.zhuangjialong.integration.department.service;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.model.Tree;
import com.mfangsoft.zhuangjialong.integration.department.model.DepartmentEntity;

public interface DepatmentService {
	
	
	
	Boolean addDepatment(DepartmentEntity departmentEntities);
	
	Boolean modifyDepatment(DepartmentEntity entity);
	
	
	Boolean removeDepatment(Long id);
	
	
	Boolean addPosition(DepartmentEntity entity);
	
	
	Boolean modifyPosition(DepartmentEntity entity);
	
    List<Tree> queryDepOrPosition(DepartmentEntity entity); 
    
    
    List<DepartmentEntity>  getDepForCombox();
    
    List<DepartmentEntity> getPositionForCombox(Long id);
    
    
   List<DepartmentEntity> queryDepOrPositionList(DepartmentEntity entity); 
   
}
