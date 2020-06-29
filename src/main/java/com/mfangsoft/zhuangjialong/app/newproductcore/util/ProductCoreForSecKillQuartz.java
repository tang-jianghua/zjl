package com.mfangsoft.zhuangjialong.app.newproductcore.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionSeckillTimeEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionSeckillTimeEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年12月2日
 * 
 */

public class ProductCoreForSecKillQuartz {

	@Autowired
	PromotionSeckillTimeEntityMapper seckillTimeEntityMapper;
	@Autowired
	PromotionSeckillProductEntityMapper seckillProductEntityMapper;
	@Autowired
	ProductCoreService productCoreServiceImpl;
	
	public void updateProductPrice() {
		List<PromotionSeckillTimeEntity> times = seckillTimeEntityMapper.selectAtNowForProductCore();
		if (times.size() > 0) {
			for (int i = 0; i < times.size(); i++) {
/*				if (times.get(i).getPstart_time().getTime() - times.get(i).getNow_time().getTime() <= 2000
						&& times.get(i).getPstart_time().getTime() - times.get(i).getNow_time().getTime() >= 2000) {
*/				if (times.get(i).getPstart_time().getTime() - times.get(i).getNow_time().getTime() == 2000) {
					if (i == 0 || times.get(i).getPstart_time() != times.get(i - 1).getPend_time()) {
						List<Map<String, Object>> list = seckillProductEntityMapper
								.selectProductAndPriceByTimeId(times.get(i).getId());
						if (list.size() > 0) {
							productCoreServiceImpl.updatePromotionPricesDocs(list);
						}
					}

				}
/*if (times.get(i).getPend_time().getTime() - times.get(i).getNow_time().getTime() <= 2000
&& times.get(i).getPend_time().getTime() - times.get(i).getNow_time().getTime() >= 0) {
*/				if (times.get(i).getPend_time().getTime() - times.get(i).getNow_time().getTime() == 2000) {
					if (i == times.size() - 1 || times.get(i).getPend_time() != times.get(i + 1).getPstart_time()) {
						List<Map<String, Object>> list = seckillProductEntityMapper
								.selectProductAndOldPriceByTimeId(times.get(i).getId());
						if (list.size() > 0) {
							productCoreServiceImpl.updatePromotionPricesDocs(list);
						}
					}
				}
			}
		}
	}
}
