package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;
@WriterRepository
public interface BrandProdSalesAttrEntityMapper extends BaseBrandProdSalesAttrEntityMapper  {
	
	
	
	Map<String,Object>  getColorByProductId(Long product_id);
	
	
	List<BrandProdSalesAttrEntity> getBrandProdSalesAttrByProductId(Long product_id);

	/**
	 * 	
	 * @description：获取产品的所有颜色型号
	 * @param：产品id
	 */
	List<String>  getColorModelByProductId(Long product_id);
	/**
	 * 	
	 * @description：获取产品的所有颜色
	 * @param：产品id
	 */
	List<String>  getColorsByProductId(Long product_id);
	/**
	 * 	
	 * @description：获取产品的所有规格
	 * @param：产品id
	 */
	List<String> getStandardsByProductId(Long product_id);
    /**
     * 	
    * @description：获取产品的所有型号
    * @param：产品id
     */
	 List<String>  getModelByProductId(Long product_id);
	 
	  /**
	     * 	
	    * @description：获取产品某颜色型号下的规格
	    * @param：产品id
	     */
	 List<BrandProdSalesAttrEntity> getStandardByProductId(BrandProdSalesAttrEntity attrEntity);
	 
	 /**
	     * 	
	    * @description：返回某销售属性的库存价格信息
	    * @param：销售属性id
	     */
	 Map<String,Object> getAppSalesAttrById(Long id);
	 
	 
	 int updateProdSalesAttr(Long id);
	 
	 /*
	  * 根据id获取产品id
	  */
	 Long getProductIdBySaleId(Long id);
	 
	 /*
	  * 通过产品id获取销售属性
	  */
	 List<BrandProdSalesAttrEntity> selectProductSalesByProductId(Long product_id);

}
