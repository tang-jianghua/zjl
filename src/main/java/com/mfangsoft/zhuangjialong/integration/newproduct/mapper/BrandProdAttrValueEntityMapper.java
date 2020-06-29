package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueEntity;

@WriterRepository
public interface BrandProdAttrValueEntityMapper extends BaseBrandProdAttrValueEntityMapper{
	List<String> selectValuesByProductId(Long product_id);
	
	
	List<BrandProdAttrValueEntity> selectProdattrValueByProductId(Long product_id);
	
	
	BrandProdAttrValueEntity selectProdattrValueByProductIdAndAttrId(BrandProdAttrValueEntity attrValueEntity);
	
	
	int deleteProdattValueByProductId(Long product_id);
	
	/*
	 * 根据产品id查询相关属性
	 */
	Map<String, String> selectValuesByProductIdForProductCore(Long product_id);
}
