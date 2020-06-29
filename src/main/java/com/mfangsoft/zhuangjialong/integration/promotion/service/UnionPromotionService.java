package com.mfangsoft.zhuangjialong.integration.promotion.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionPromotion;

public interface UnionPromotionService {
	
	
	
	Boolean  addUnionPromotion(UnionPromotion unionPromotion);
	
	Boolean  modifyUnionPromotion(UnionPromotion unionPromotion);
	
	Page<Map<String,Object>> queryUnionPromotionForPage(Page<Map<String,Object>> map);
	
	Page<Map<String,Object>> queryProductForPromotionForPage(Page<Map<String,Object>> map);
	
	List<Map<String, Object>> queryBrandName();
	
	
	Boolean addUnionPromotionProduct(UnionPromotion unionPromotion);
	
	Boolean modifyUnionPromotionProduct(UnionPromotion unionPromotion);
	
	Boolean deleteUnionPromotion(Integer id);
	/**
  	 * 修改下架 上架状态,0:下架;1:上架 
  	 * unionPromotionID 联盟活动ID
  	 * onOffFlag 0:下架;1:上架
  	 */
	Boolean modifyOnOffFlag(Integer unionPromotionID,Integer onOffFlag);
}
