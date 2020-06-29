package com.mfangsoft.zhuangjialong.integration.newproduct.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;

public interface BrandProductCopy {
	
	
	Boolean addCopyBrandProduct(Long frombrandId,Long tobrandId,Long class_id);
	
	
	
	Boolean addCopyEntToBrandProduct(Long brandId,Long entId,Long class_id) throws IllegalAccessException, InvocationTargetException;
	
	
	
	Boolean addFromEntToBrandProduct(Page<Map<String,Object>> page) throws IllegalAccessException, InvocationTargetException;
	
	
	Boolean addFromEntToBrandProduct(List<Long> list) throws IllegalAccessException, InvocationTargetException;
	
	
	//Boolean addFromEntToBrandProduct(List<Long> list,List<Long> brand_id) throws IllegalAccessException, InvocationTargetException;

}
