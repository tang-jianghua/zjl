package com.mfangsoft.zhuangjialong.system.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

public interface SysConstantService {
	
	
	
	
	sysConstantEntity getSysConstantById(Integer id);
	
	
	sysConstantEntity getSysConstantByKey(String key);
	
	
	List<sysConstantEntity> getSysConstantByType(String type);
	
	
	Map<String,Object> getSysConstantByKeyForMap(String key);

}
