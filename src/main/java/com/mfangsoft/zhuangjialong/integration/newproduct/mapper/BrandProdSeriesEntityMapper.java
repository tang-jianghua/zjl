package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface BrandProdSeriesEntityMapper  extends BaseBrandProdSeriesEntityMapper {
  
    
	
    List<Long>  selectByProductId(Long product_id); //根据产品id获取所有的系列id
    
    
    List<Map<String, String>> selectAll();//查询产品库所有现有产品的自定义系列
    
}