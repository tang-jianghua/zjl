package com.mfangsoft.zhuangjialong.integration.promotion.utils;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;

public class ZhekouPriceByQuartz {

	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	@Autowired
	private BrandNewProductEntityMapper brandNewProductEntityMapper;
	
	
	public void updateZhekouPrice(){

		List<PromotionZhekouParam> list = promotionZhekouEntityMapper.selectZheKouPromotionProduct2();
		
		if(list != null){
			for(PromotionZhekouParam param : list){
				
				if(param.getIsAllProduct() != null && param.getIsAllProduct().equals(1)){
					//全部产品
					
//					QuestsManagerBean.addQuest(new Quest() {
//
//						@Override
//						public boolean execute() {
//							JPushUtil.sendMessage(o.getPlatform(), o.getPushstr(), MessageConstant.orderExpireTitle,
//									MessageFormat.format(MessageConstant.orderExpireContent, o.getOrder_code()));
//							return true;
//						}
//
//						@Override
//						public boolean condition() {
//							return true;
//						}
//
//						@Override
//						public boolean delete() {
//							return true;
//						}
//					});
					
					List<ProductListModel> list_product = brandNewProductEntityMapper.selectPriceInfoByBrandId(param.getBrand_id());
					if (list_product != null && list_product.size() > 0) {
//						productCoreServiceImpl.updatePromotionPricesDocs(list);
					}
					
					
				}else{
					
					
				}
				
			}
		}
		
		
	}
}
