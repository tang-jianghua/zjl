package com.mfangsoft.zhuangjialong.integration.enterprise.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseTwoEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月21日
* 
*/

@WriterRepository
public interface EnterpriseTwoEntityMapper  extends BaseEnterpriseTwoEntityMapper{

	
	
	 List<Map<String,Object>>  selectEnterpriseTwoByOne(Long id);
	    /*
	     * 根据一级id获取二级内容
	     * 
	     */
	    List<EnterpriseTwoEntity> selectEnterpriseTwoByOneId(Long one_Id);
	    
	    int removeEnterpriseTwoByOneId(Long id);


}
