package com.mfangsoft.zhuangjialong.integration.brand.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.brand.model.BaseBrandStateApplyEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyEntity;

public interface BrandService {
	
	
	Boolean  addBrand(BrandEntity brandEntity);
	
	BrandEntity getBrandById(Long id);
	
	Integer getbrandIdintifyType();
	
	Boolean  modifyBrand(BrandEntity brandEntity);
	
	
	Page<Map<String,Object>>  queryBrandForPage(Page<Map<String,Object>> page);
	
	
	Boolean modifyopenOrCloseBrand(Long id,Integer state);
	
	
	
	BrandEntity selectBrandNameByUserId(Long userId);
	
	List<BrandEntity> selectBrandName();
	
	Boolean  modifyBackBrand(BrandEntity brandEntity);
	
	/*
	 * 品牌上架
	 */
	ResponseMessage<String> modifyBrandOnShelf(BrandEntity brand);

	/*
	 * 申请品牌下架
	 */
	Integer modifyBrandOffingShelf(BaseBrandStateApplyEntity brand);

	/*
	 * 品牌下架
	 */
	Boolean modifyBrandOffShelf(BaseBrandStateApplyEntity brand);

	/*
	 * 获取申请下架的品牌
	 */
	Page<Map<String, Object>> getApplyOffStateBrands(Page<Map<String, Object>> page);
}
