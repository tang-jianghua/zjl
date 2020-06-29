package com.mfangsoft.zhuangjialong.integration.entnewproduct.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProductEntity;
@WriterRepository
public interface EntNewProductEntityMapper extends BaseEntProductEntityMapper {

		EntProductEntity selectAllByPrimaryKey(Long id);
	
	
	
		List<Map<String,Object>> selectBrandProductForPage(Page<Map<String,Object>> map);
		
		
		List<Map<String,Object>> getBrandDevProductListForPage(Page<Map<String,Object>> map);
		
		
		
		List<Map<String,Object>> selectViewbyproduct(Long product_id);
		
		
		List<EntProdAttrValueName> selectproductAttrValueName();
		
		
		int updateProdBold(Map<String,Object> map);
		
		
		EntProdAttrValueName  selectproductAttrValueNameByProductId(Long id);
		
		
		List<Long>  selectProductByEntId(Long entId);

}
