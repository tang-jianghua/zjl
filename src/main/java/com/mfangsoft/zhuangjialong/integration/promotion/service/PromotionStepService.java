package com.mfangsoft.zhuangjialong.integration.promotion.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepConditionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionStepProductEntity;

@Service
public interface PromotionStepService {

	public ResponseMessage<String> addPlatformStepPromotion(PromotionEntityStepParam record);
	
	public List<Map<String,Object>> queryPlatformStepPromotionListByPage(Page<Map<String,Object>> page);
	
	public Map<String, Object> getPlatformStepPromotionByid(Long id);
	
	public void modifyPlatformPromotion(PromotionEntityStepParam param);
	
	public boolean deletePlatformPromotion(PromotionStepConditionEntity param);
	
	public boolean registerPlatformPromotion(PromotionEntity param);
	
	public ResponseMessage<String> cancleRegisterPlatformPromotion(Long id);
	
	public boolean addStepPromotionProducts(PromotionEntityStepParam param);
	
	public List<Product> getStepPromotionProductList(PromotionStepProductEntity param);
	
	public PromotionEntityStepParam getStepPromotionProductForPlatform(PromotionEntityStepParam param);
	
	
	public Page<Map<String,Object>> selectAllProductForBrandToAddByPage(Page<Map<String,Object>> page);

	public boolean removeStepPromotionProductForBrand(List<Long> param);
	
	public boolean modifyStepPromotionProductForBrand(List<PromotionStepProductEntity> param);	
	
	
}
