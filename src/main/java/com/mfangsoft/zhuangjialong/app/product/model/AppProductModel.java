package com.mfangsoft.zhuangjialong.app.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月10日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class AppProductModel extends BaseBrandProductEntity{

	    private String product_name;//产品标题
	 
	    private Float quality;//质量评分

	    private Float service;//服务评分
	    
	    private Long sort;//排序号
	    
	    private String brand_name;//品牌名称
	    
	    private Integer  isNew;//是否新品
	    
	    private String unit_name;//单位
	    
	    private List<AppProdSeriesModel> brandProdSeriesEntities;//产品系列

	    private List<AppProdImgModel> brandProdImgEntities;//产品图片

		/**
		 * @return the product_name
		 */
		public String getProduct_name() {
			return product_name;
		}

		/**
		 * @param product_name the product_name to set
		 */
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}

		/**
		 * @return the quality
		 */
		public Float getQuality() {
			return quality;
		}

		/**
		 * @param quality the quality to set
		 */
		public void setQuality(Float quality) {
			this.quality = quality;
		}

		/**
		 * @return the service
		 */
		public Float getService() {
			return service;
		}

		/**
		 * @param service the service to set
		 */
		public void setService(Float service) {
			this.service = service;
		}

		/**
		 * @return the sort
		 */
		public Long getSort() {
			return sort;
		}

		/**
		 * @param sort the sort to set
		 */
		public void setSort(Long sort) {
			this.sort = sort;
		}

		/**
		 * @return the brand_name
		 */
		public String getBrand_name() {
			return brand_name;
		}

		/**
		 * @param brand_name the brand_name to set
		 */
		public void setBrand_name(String brand_name) {
			this.brand_name = brand_name;
		}

		/**
		 * @return the isNew
		 */
		public Integer getIsNew() {
			return isNew;
		}

		/**
		 * @param isNew the isNew to set
		 */
		public void setIsNew(Integer isNew) {
			this.isNew = isNew;
		}

		/**
		 * @return the unit_name
		 */
		public String getUnit_name() {
			return unit_name;
		}

		/**
		 * @param unit_name the unit_name to set
		 */
		public void setUnit_name(String unit_name) {
			this.unit_name = unit_name;
		}

		/**
		 * @return the brandProdSeriesEntities
		 */
		public List<AppProdSeriesModel> getBrandProdSeriesEntities() {
			return brandProdSeriesEntities;
		}

		/**
		 * @param brandProdSeriesEntities the brandProdSeriesEntities to set
		 */
		public void setBrandProdSeriesEntities(List<AppProdSeriesModel> brandProdSeriesEntities) {
			this.brandProdSeriesEntities = brandProdSeriesEntities;
		}

		/**
		 * @return the brandProdImgEntities
		 */
		public List<AppProdImgModel> getBrandProdImgEntities() {
			return brandProdImgEntities;
		}

		/**
		 * @param brandProdImgEntities the brandProdImgEntities to set
		 */
		public void setBrandProdImgEntities(List<AppProdImgModel> brandProdImgEntities) {
			this.brandProdImgEntities = brandProdImgEntities;
		}
	    
	    
}
