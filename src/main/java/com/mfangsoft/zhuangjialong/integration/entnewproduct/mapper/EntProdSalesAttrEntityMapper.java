package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdSalesAttrEntity;


@WriterRepository
public interface EntProdSalesAttrEntityMapper extends BaseEntProdSalesAttrEntityMapper {
	
	
	
	List<EntProdSalesAttrEntity> getBrandProdSalesAttrByProductId(Long product_id);

}
