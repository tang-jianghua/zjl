package com.mfangsoft.zhuangjialong.integration.pointmall.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointConvertEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.BasePointMallEntity;
import com.mfangsoft.zhuangjialong.integration.pointmall.model.PointMallEntity;

public interface PointMallService {
	
	Boolean  addPoinMall(PointMallEntity pointMallEntity);
	
	
	Boolean  modifyPoinMall(PointMallEntity pointMallEntity);
	
	
	Page<Map<String,Object>> queryPoinMall(Page<Map<String,Object>> page);
	
	
	
	List<Map<String,Object>> queryOperator();
	
	
	
	List<Map<String,Object>> queryFlowPackage(String operator_code);
	
	
	List<Map<String,Object>> queryPartner();
	
	
	List<Map<String,Object>> queryBrand(List<Long> partnerId);
	
	List<Map<String,Object>> queryBrand();
	
	List<Map<String,Object>> queryShop(List<Long> brandId);
	
	
	
	Page<Map<String,Object>> queryPointConverPage(Page<Map<String,Object>> page);
	
	
	Long    queryPointConvertByConvertCode(String convertCode);
	
	
	Boolean   updatePoinConvert(Long id);
	
	
	PointMallEntity  getPoinMapById(Long id);
	
	
	Boolean   updatePointMallProduct(Long id,Integer state);
	
	
	
	
	
	
	

}
