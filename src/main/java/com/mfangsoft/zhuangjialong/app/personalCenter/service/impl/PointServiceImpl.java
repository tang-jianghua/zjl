package com.mfangsoft.zhuangjialong.app.personalCenter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PointEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.PointEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.service.PointService;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月2日
* 
*/
@Service
public class PointServiceImpl implements PointService{
        
	 @Autowired
	private PointEntityMapper pointEntityMapper;
	/* (non-Javadoc)
	 * @see com.mfangsoft.zhuangjialong.app.personalCenter.service.PointService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public PointEntity selectByPrimaryKey(Long id) {
		return	pointEntityMapper.selectByPrimaryKey(id);
	}
   public PointEntity selectByCustomerId(long customer_id){
	    return pointEntityMapper.selectByCustomerId(customer_id);
   }
}
