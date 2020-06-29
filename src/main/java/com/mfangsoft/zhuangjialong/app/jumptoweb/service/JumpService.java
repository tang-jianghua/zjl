package com.mfangsoft.zhuangjialong.app.jumptoweb.service;


import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mfangsoft.zhuangjialong.app.jumptoweb.model.ParameterModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月14日
* 
*/

public interface JumpService {
	  /**
     * 通过type获取数据  
	 * @throws UnsupportedEncodingException 
     *
     * @MLTH_generated
     */
	Map<String, Object>  selectByType(ParameterModel record) throws JsonProcessingException, UnsupportedEncodingException;
	
	  /**
     * 通过type获取数据  
	 * @throws UnsupportedEncodingException 
     *
     * @MLTH_generated
     */
	String makeUrlByType(ParameterModel record) throws JsonProcessingException, UnsupportedEncodingException;
}
