package com.mfangsoft.zhuangjialong.app.seller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.seller.service.ShopGuiderService;
import com.mfangsoft.zhuangjialong.integration.enums.ShopGuiderCertificationState;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopGuiderMapper;
import com.mfangsoft.zhuangjialong.integration.shop.model.BaseShopGuiderEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月27日
* 
*/
@Service
public class AppShopGuiderServiceImpl implements ShopGuiderService{

	@Autowired
	ShopGuiderMapper shopGuiderMapper;

	@Override
	public Boolean updateShopGuider(ShopGuiderModel shopGuiderModel) {
		shopGuiderModel.setCertification_state(ShopGuiderCertificationState.Checking.getIndex());
		return	shopGuiderMapper.updateByPrimaryKeySelective(shopGuiderModel)>0;
	}

	@Override
	public ShopGuiderModel getShopGuiderCertificationInfo(ShopGuiderModel guiderModel) {
		return shopGuiderMapper.selectCertificationInfoBySellerId(guiderModel.getId());
	}

	@Override
	public int getShopGuiderCertificationState(ShopGuiderModel guiderModel) {
		return shopGuiderMapper.selectCertificationStateBySellerId(guiderModel.getId());
	}
	
}
