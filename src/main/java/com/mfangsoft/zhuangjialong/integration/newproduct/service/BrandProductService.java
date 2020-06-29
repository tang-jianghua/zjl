package com.mfangsoft.zhuangjialong.integration.newproduct.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.coupons.model.BrandCouponsEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductEntity;

public interface BrandProductService {
	
   Boolean  addBrandProduct(BrandProductEntity brandProductEntity);
   
   
   Boolean  addBrandProductForCopy(BrandProductEntity brandProductEntity);
   
   Boolean  modfiyBrandProduct(BrandProductEntity brandProductEntity);
   
/*   Boolean  modfiyBrandProductForCopy(BrandProductEntity brandProductEntity);*/
   
   
   BrandProductEntity getBrandProductById(Long id);
   
   
   Page<Map<String,Object>> getBrandProductForPage(Page<Map<String,Object>> page);
   
   
   Boolean removeBrandProdSales(Long id);
   
   Page<Map<String,Object>> getBrandDevProductListForPage(Page<Map<String,Object>> page);
   
   Boolean updateBrandProduct(List<Long> list,Integer state);
   
   BrandProdAttrValueName  selectproductAttrValueNameByProductId(Long id);
	
   List<BrandProdAttrValueName> selectproductAttrValueName();
   
   
   
    Boolean deleteProduct(Long id);
    
    
    List<Map<String,Object>> selectAll(Map<String,Object> map);
    
    List<Map<String,Object>> selectEntAll(Map<String,Object> map);
    
    
    Boolean deleteEntProduct(Long id);
    
    
    Boolean selectPromotionProductByProductId(List<Long>  product_id);
    
    /**
     * banner跳转
     * @param page
     * @return
     */
    Page<Map<String,Object>> selectProductForBanner(Page<Map<String,Object>> page);
    
    
    BrandProductEntity getBrandProductBannerById(Long id);
    
    
    
	
	Page<Map<String,Object>>  getBrandPaoductBrandMain(Page<Map<String,Object>> page);
	
	/**
	 * 设置以后的列表
	 * @param page
	 * @return
	 */
	Page<Map<String,Object>>  getBrandPaoductBrandMainList(Page<Map<String,Object>> page);
	
	
    
    
    
    


}
