package com.mfangsoft.zhuangjialong.integration.promotion.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouProductEntity;

@Service
public interface PromotionZhekouService {

	public ResponseMessage addZhekouPromotion(PromotionZhekouEntity record, ResponseMessage responseMessage);
	
	public List<Map<String,String>> queryZhekouPromotionForPage(Page<Map<String,String>> param);
	
	public PromotionZhekouEntity queryOneZhekouPromotion(Long id);
	
	public ResponseMessage modifyZhekouPromotion(PromotionZhekouEntity record, ResponseMessage responseMessage);
	
	public ResponseMessage  addZhekouPromotionPorduct(List<PromotionZhekouProductEntity> param, ResponseMessage responseMessage);
	
	public Page<Map<String,Object>> queryZhekouPromotionPorduct(Page<Map<String,Object>> page);
	
	public List<Map<String, Object>> queryAllZhekouPorductBrandToChooseByPage(Page<Map<String,Object>> page);
	
	public void deleteZhekouPromotionPorduct(List<Long> id_list);
	
}
