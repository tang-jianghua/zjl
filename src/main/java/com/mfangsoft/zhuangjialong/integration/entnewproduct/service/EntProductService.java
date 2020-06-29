package com.mfangsoft.zhuangjialong.integration.entnewproduct.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProductEntity;

public interface EntProductService {
	
	
		Boolean  addEntProduct(EntProductEntity brandProductEntity);
		
		
		Boolean  addEntProductForCopy(EntProductEntity brandProductEntity);
	   
	    Boolean  modfiyEntProduct(EntProductEntity brandProductEntity);
	   
	   
	    EntProductEntity getEntProductById(Long id);
	   
	   
	   Page<Map<String,Object>> getEntProductForPage(Page<Map<String,Object>> page);
	   
	   Page<Map<String,Object>> getBrandDevProductListForPage(Page<Map<String,Object>> page);
	   
	   
	   Boolean  removeSalesAttr(Long id);
	   
	   List<EntProdAttrValueName> selectproductAttrValueName();
	   
	   EntProdAttrValueName  selectproductAttrValueNameByProductId(Long id);
	   int updateProdBold(Map<String,Object> map);

}
