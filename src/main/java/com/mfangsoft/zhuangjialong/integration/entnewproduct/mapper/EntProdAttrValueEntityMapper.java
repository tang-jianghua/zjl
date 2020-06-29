package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.BaseEntProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueEntity;

@WriterRepository
public interface EntProdAttrValueEntityMapper extends  BaseEntProdAttrValueEntityMapper {
	
	
	List<EntProdAttrValueEntity> selectProdattrValueByProductId(Long product_id);
	
	
	
	EntProdAttrValueEntity selectProdattrValueByProductIdAndAttrId(EntProdAttrValueEntity attrValueEntity);
	
	
	int deleteProdattValueByProductId(Long product_id);

}
