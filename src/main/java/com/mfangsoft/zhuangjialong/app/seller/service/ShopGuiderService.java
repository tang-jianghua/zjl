package com.mfangsoft.zhuangjialong.app.seller.service;

import com.mfangsoft.zhuangjialong.integration.shop.model.BaseShopGuiderEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月27日
* 
*/

public interface ShopGuiderService {

	/*
	 * 店铺导购认证
	 */
     Boolean updateShopGuider(ShopGuiderModel shopGuiderModel);
     
     /*
      * 查询店铺导购认证信息
      */
     ShopGuiderModel getShopGuiderCertificationInfo(ShopGuiderModel guiderModel);
     
     /*
      * 查询店铺导购认证状态
      */
     int getShopGuiderCertificationState(ShopGuiderModel guiderModel);
}
