package com.mfangsoft.zhuangjialong.app.DIYdesign.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.DIYdesign.model.ClassBrandModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月21日
* 
*/

public interface DIYService {
       
	public List<ClassBrandModel> getDIYClassBrand(Map<String, String> param);
	
	public Long getProductIdByModel(Map<String, String> param);
	
}
