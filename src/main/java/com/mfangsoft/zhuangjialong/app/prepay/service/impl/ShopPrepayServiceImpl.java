package com.mfangsoft.zhuangjialong.app.prepay.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.prepay.mapper.ShopPrepayEntityMapper;
import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
import com.mfangsoft.zhuangjialong.app.prepay.service.ShopPrepayService;

@Service
public class ShopPrepayServiceImpl implements ShopPrepayService{
	@Autowired
	ShopPrepayEntityMapper shpPerpayMapper;
	
	@Override
	public Boolean addShopPrepay(ShopPrepayEntity shopPerpay) {
		// TODO Auto-generated method stub
		shopPerpay.setState(0);
		shopPerpay.setCreate_time(new Date());
		return shpPerpayMapper.insertSelective(shopPerpay) > 0 ? true:false;
	}

}
