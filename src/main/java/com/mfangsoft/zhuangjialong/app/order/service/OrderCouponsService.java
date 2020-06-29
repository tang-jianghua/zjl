package com.mfangsoft.zhuangjialong.app.order.service;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.order.model.OrderCouponsEntity;

/**
* @description：
* @author：Liyanchen 
* @date：2016年6月14日
* 
*/
@Service
public interface OrderCouponsService {
	
	public boolean insertSelective(OrderCouponsEntity entity);
}
