package com.mfangsoft.zhuangjialong.integration.region.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;

public interface RegionService {
	
	
	
	List<RegionEntity>  getProvince();
	
	
	
	List<RegionEntity>  getCity(String code);
	
	
	List<RegionEntity>  getCounty(String code);
	
	

	Map<String,RegionEntity> getName(String code);


	
	/**
	 * 
	* @description：根据区域编码获取完整区域名
	* @param：String code
	 */
	String getWholeAddress(String code);
	
	
	
	String selectreginName(String region_code);


}
