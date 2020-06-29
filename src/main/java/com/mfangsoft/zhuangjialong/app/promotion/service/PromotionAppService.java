package com.mfangsoft.zhuangjialong.app.promotion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mfangsoft.zhuangjialong.app.product.model.Product;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionNoteEntity;
import com.mfangsoft.zhuangjialong.app.promotion.model.PromotionTimer;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionEntityStepParam;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillProductEntity;

@Service
public interface PromotionAppService {

	public PromotionTimer queryMainBrandPromotion(PromotionTimer param);
	
	public List<PromotionTimer> getpromotiontime(PromotionTimer param);
	
	public PromotionTimer getpromotiontime2(PromotionTimer param);
	
	public List<Product> queryPlatformPromotionProduct(Page<Product> page);
	
	public Long addPromotionCustomerNote(PromotionNoteEntity param);
	
	public List<PromotionTimer> getpromotioncustomernoteproducts(Long customer_id);
	
	public Integer removepromotioncustomernote(PromotionNoteEntity param);
	
	public ResponseMessage<String> checkpromotionconditionforcustomer(PromotionSeckillProductEntity param);
	
	public List<PromotionEntityStepParam> querypromotionforfirstpage(String region_code);
	
	public PromotionEntityStepParam getpromotionfordetail(PromotionEntityStepParam param);
	
	public List<Product> getapppromotionproductforpage(Page<Product> page);
	
}
