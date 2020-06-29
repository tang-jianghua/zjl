package com.mfangsoft.zhuangjialong.app.coupons.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.coupons.model.FirstPageCouponsListParam;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月9日
* 
*/

public interface CouponsService {
    /**
     * 获取品牌优惠券
     *
     */
    List<BrandCouponsModel> selectBrandCoupons(CustomerCollectionEntity param);
    
    /**
     * 获取品牌红包
     *
     */
    List<BrandCouponsModel> selectBrandRedbags(CustomerCollectionEntity param);
    
    
    /**
     * 领取品牌优惠券/红包
     *
     */
    Boolean addBrandCoupons  (BrandCouponsModel param);
    
    /**
     * 发送优惠券/红包消息
     *
     */
    Boolean sendMessage(BrandCouponsModel param);
    
    /**
	 * 查询活动通用券
	 * 
	 */
    BrandCouponsModel queryPromotionCoupons();
    
    List<Map<String,Object>> queryFirstPageCouponsList(Page<Map<String,Object>> page);
    
    Map<String,Object> queryOneFirstPageCoupons(Map<String,Object> param);
    
    List<Map<String,Object>> queryOneFirstPageCouponsShops(Map<String,Object> param);
    
}
