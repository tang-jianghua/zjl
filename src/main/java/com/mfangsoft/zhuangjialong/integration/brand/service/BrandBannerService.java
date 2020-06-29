package com.mfangsoft.zhuangjialong.integration.brand.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandMainProductEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyProductEntity;

public interface BrandBannerService {
	
	
	
	Boolean  addBrandBanner(BrandBannerEntity bannerEntity);
	
	
	Boolean  modfiyBrandBanner(BrandBannerEntity bannerEntity);
	
	Boolean  removeBrandBanner(Long id);
	
	List<BrandBannerEntity>  queryBrandBanner();
	
	
	
	Boolean addBrandMainProduct(BrandMainProductEntity brandMainProductEntity);
	
	
	
	Boolean modfiyBrandMainProduct(BrandMainProductEntity brandMainProductEntity);
	
	
	Boolean removeBrandMainProduct(BrandMainProductEntity brandMainProductEntity);
	
	
	Page<Map<String,Object>> getBrandMainProduct(Page<Map<String,Object>> page);
	
	
	
	List<HotClassifyEntity> queryHostClassify();
		

	
	Boolean addHotClassify(HotClassifyEntity classifyEntity);
	
	
	Boolean modfiyHotClassify(HotClassifyEntity classifyEntity);
	
	Boolean removeHotClassify(Long  id);
	
	
	Boolean addHotClassifyProduct(HotClassifyProductEntity classifyProductEntity);
	
	
	
	Page<Map<String,Object>> getBrandHotClassifyProductPage(Page<Map<String,Object>> page);

	Boolean batchDeleteBrandMainProduct(List<Long> brandMainProductIDList);
}



