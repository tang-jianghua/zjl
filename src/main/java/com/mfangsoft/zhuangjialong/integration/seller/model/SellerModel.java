package com.mfangsoft.zhuangjialong.integration.seller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerConstructInfoEntity;
import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopGuiderModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月22日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class SellerModel extends SellerEntity{

	private SellerConstructInfoEntity constructInfoEntity;

	private ShopGuiderModel shopGuider;
	
	
	/**
	 * @return the shopGuider
	 */
	public ShopGuiderModel getShopGuider() {
		return shopGuider;
	}

	/**
	 * @param shopGuider the shopGuider to set
	 */
	public void setShopGuider(ShopGuiderModel shopGuider) {
		this.shopGuider = shopGuider;
	}

	/**
	 * @return the constructInfoEntity
	 */
	public SellerConstructInfoEntity getConstructInfoEntity() {
		return constructInfoEntity;
	}

	/**
	 * @param constructInfoEntity the constructInfoEntity to set
	 */
	public void setConstructInfoEntity(SellerConstructInfoEntity constructInfoEntity) {
		this.constructInfoEntity = constructInfoEntity;
	}
	
	
}
