package com.mfangsoft.zhuangjialong.integration.shop.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.shop.model.ShopEntity;

public interface ShopService
{
	
	/**
	 * 添加店铺信息
	 * @param shopEntity
	 * @return
	 */
	Boolean addShop(ShopEntity shopEntity);
	
	/**
	 * 修改店铺信息
	 * @param shopEntity
	 * @return
	 */
	Boolean modifyShop(ShopEntity shopEntity);
	
	/**
	 * 通过Id获取店铺信息
	 * @param id
	 * @return
	 */
	ShopEntity getShopById(Long id);
	
	/**
	 * 分页查询店铺信息
	 * @param page
	 * @return
	 */
	Page<Map<String, Object>> getShopForPage(Page<Map<String, Object>> page);
	
	
	Boolean checkShop(Long id, Integer state);
	
	/**
	 * 开启或者关闭店铺账号
	 * @param id
	 * @param state
	 * @return
	 */
   Boolean openOrCloseShop(Long id, Integer state);
   
   
   List<ShopEntity> getShopName();
   
   
   ShopEntity selectshopByUserId(Long userId);
   
   Map<String,Object> getShopEntityById(Long shopId);
	
   
   Boolean modifyBackShop(ShopEntity shopEntity);
	
   /**
    * 修改店铺状态
    * @param shopEntity
    * @return
    */
   Boolean modifyShopState(ShopEntity shopEntity);
}
