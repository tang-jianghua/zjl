package com.mfangsoft.zhuangjialong.app.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.order.mapper.OrderCouponsEntityMapper;
import com.mfangsoft.zhuangjialong.app.order.model.OrderCouponsEntity;
import com.mfangsoft.zhuangjialong.app.order.service.OrderCouponsService;

/**
* @description：
* @author：Liyanchen 
* @date：2016年6月14日
* 
*/
@Service
public class OrderCouponsServiceImpl implements OrderCouponsService {

	@Autowired
	OrderCouponsEntityMapper orderCouponsEntityMapper;
	@Override
	public boolean insertSelective(OrderCouponsEntity entity) {
		orderCouponsEntityMapper.insertSelective(entity);
		
		return false;
	}

}
