package com.mfangsoft.zhuangjialong.integration.promotion.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.promotion.model.BrandProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.model.ProductState;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillTimeEntity;
@Service
public interface PromotionService {

	public ResponseMessage<String> addPromotion(PromotionEntity promotion);
	
	public List<Map<String,String>> queryplatformpromotionListByPage(Page<Map<String,String>> page);
	
	public PromotionEntity getPlatformPromotionById(Long id);
	
	public boolean modifyPromotion(PromotionEntity promotion)  throws ParseException;
	
	public List<PromotionTimer> selectBrandListOfOnepromotionForPlatform(PromotionTimer promotion);
	
	public PromotionTimer getProductOfOnepromotionForPlatform(PromotionTimer promotion);
	
	public List<Map<String,String>> getbrandpromotionListByPage1(Page<Map<String,String>> page);
	
	public List<PromotionEntity> getbrandpromotionListByPage2(Page<PromotionEntity> page);
	
	public List<Map<String,Object>> getbrandpromotionproductListByPage(Page<Map<String,Object>> page);
	
	public List<Map<String,String>> selectProductOfOnepromotionForBrandByPage(Page<Map<String,String>> page);
	
	public Map<String,Integer> getProductInfoOfOnepromotionForBrand(Long pid);
	
	public ResponseMessage<String> addmodifybrandpromotionproductList(PromotionSeckillProductEntity param);
	
	public ResponseMessage<String> removebrandpromotionproduct(List<PromotionSeckillProductEntity> param);
	
	public boolean modifyonepromotionproduct(List<PromotionSeckillProductEntity> param);
	
    Page<Map<String,Object>> queryPromotionLink(Page<Map<String,Object>> page);
	
    public boolean registerPromotionforbrand(PromotionSeckillTimeEntity param);
    
    public ResponseMessage<String> cancleRegisterPromotionforbrand(PromotionSeckillTimeEntity param);
	
	
}
