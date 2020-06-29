package com.mfangsoft.zhuangjialong.integration.shop.service;


import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.seller.model.SellerModel;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月23日
* 
*/

public interface ShopGuiderService {

	
	/*
	 * 添加店面导购
	 */
	ResponseMessage<String> addShopGuider(SellerModel sellerModel);
	
	/*
	 * 编辑店面导购
	 */
	ResponseMessage<String> modifyShopGuider(SellerModel sellerModel);
	
	/*
	 * 查询店面导购
	 */
	Page<ShopGuiderModel> getShopGuideresForPage(Page<ShopGuiderModel> page);
	
	/*
	 * 查询店面导购
	 */
	Map<String, Object> getShopGuider(Long id);
	/*
	 * 查询店面导购
	 */
	 List<Map<String, Object>> queryShopGuiderForSelect();
	
	/*
	 * 编辑店面导购
	 */
	Boolean modifyShopGuiderState(Map<String, Object> sellerModel);
}
