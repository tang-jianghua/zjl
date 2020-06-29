package com.mfangsoft.zhuangjialong.integration.newproduct.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;

public interface ClassAttrService {
	
	
	
	/**
	 * 通过品类查询相关属性
	 * @param class_id
	 * @return
	 */
	List<ClassAttrEntity>  getClassAttrEntityByClassId();
	
	/**
	 * 通过属性获得属性value
	 * @param attr_id
	 * @return
	 */
	List<ClassAttrValueEntity> getClassAttrValueEntityByAttrId(Long attr_id,Integer iswrite);
	
	
	/**
	 * 通过属性获得属性value
	 * @param attr_id
	 * @return
	 */
	List<ClassAttrValueEntity> getClassAttrValueEntityforRedisByAttrId(Long attr_id,Integer iswrite);
	
	
	/**
	 * 在级联情况下调用
	 * @param parent_id
	 * @return
	 */
	List<ClassAttrValueEntity> getClassAttrValueEntityByParentId(Map<String,Long> map);
	

}
