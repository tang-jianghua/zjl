package com.mfangsoft.zhuangjialong.system.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

public class sysConstantEntityUtils {
	
	
	public static String getValue(String key,String type){
		
		
		if(key!=null){
		
		sysConstantEntity entity=(sysConstantEntity) RedisUtils.getMapValue(type, key);
		
		if(entity!=null){
			
			return entity.getValue();
		}
		}
		return null;
		
	}

}
