package com.mfangsoft.zhuangjialong.app.newproductcore.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.salesnum.model.SalesNumEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProductEntity;


/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年8月10日
* 
*/

public interface ProductCoreService {
	
    /*
     * 添加产品
     */
	public boolean addNewProductDoc(Long product_id);
	
	/*
	 * 删除产品
	 */
	public boolean deleteNewProductDoc(Long product_id);
	
	/*
	 * 删除产品
	 */
	public boolean deleteNewProductDoc(List<String> product_ids);
	
	/*
	 * 批量修改产品
	 */
	public boolean updateNewProductDocs(List<Long> product_ids);
	
	/*
	 * 修改产品
	 */
	public boolean updateNewProductDoc(Long product_id);
	
	/*
	 * 批量删除产品
	 */
	public boolean deleteNewProductDocs(List<Long> product_ids);
	
	/*
	 * 按产品id修改产品销量
	 */
	public boolean updateSalesNumDocsByProductId(Long product_id,Integer sale_num);
	
	/*
	 * 修改产品销量
	 */
	public boolean updateSalesNumDocs(List<SalesNumEntity> salesNumEntities);
	
	/*
	 * 批量修改产品活动类型
	 */
	public boolean updatePromotionTypesDocs(List<Long> product_ids);
	
	/*
	 * 修改产品状态
	 */
	public boolean updateStateDocs(List<Long> product_ids,Integer state);
	/*
	 * 修改产品状态
	 */
	public boolean updateStateDocs(List<Map<String, Object>> maps);
	
	/*
	 * 修改产品品牌状态
	 */
	public boolean updateBrandStateDoc(Long brand_id,Integer brand_state);
	/*
	 * 修改产品合伙人状态
	 */
	public boolean updatePartnerStateDoc(Long partner_id,Integer partner_state);
	
	/*
	 * 修改产品价格
	 */
	public boolean updatePromotionPricesDocs(List<Map<String, Object>> maps);
	
	/*
	 * 修改产品销售属性
	 */
	public boolean updateProductSalesPropertiesDocs(List<Map<String, Object>> maps);
	
	/*
	 * 修改产品自定义分类系列属性
	 */
	public boolean updateProductSeriesDocs(List<Map<String, String>> maps);
	
	/*
	 * 更新产品轮播图
	 */
	public boolean updateProductImagesDocs(List<Long> product_ids);
	
	/*
	 * 更新产品销量
	 */
	public boolean updateProductSaleNumDocs();

	/**
	* @description：
	* @param：
	*/
	public void updateStateDoc(Map<String, String> map);
	
}
