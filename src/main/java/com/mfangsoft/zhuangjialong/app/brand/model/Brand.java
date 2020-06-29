package com.mfangsoft.zhuangjialong.app.brand.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.Coupons;
import com.mfangsoft.zhuangjialong.app.product.model.Product;

/**
* @description：品牌模型
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Brand {
        private Long brand_id;
        private String brand_name;
        private String brand_image;
        private List<Coupons> coupons;
        private List<BrandBanner> banner;
        private List<Product> single;
        private String allinone_url;
        private  Long collect_num;
        private Double product_score;
        private Double brand_service;
        private Long class_id;
        
        
        
        
        

		/**
		 * @return the class_id
		 */
		public Long getClass_id() {
			return class_id;
		}
		/**
		 * @param class_id the class_id to set
		 */
		public void setClass_id(Long class_id) {
			this.class_id = class_id;
		}
		/**
		 * @return the product_score
		 */
		public Double getProduct_score() {
			return product_score;
		}
		/**
		 * @param product_score the product_score to set
		 */
		public void setProduct_score(Double product_score) {
			this.product_score = product_score;
		}
		/**
		 * @return the brand_service
		 */
		public Double getBrand_service() {
			return brand_service;
		}
		/**
		 * @param brand_service the brand_service to set
		 */
		public void setBrand_service(Double brand_service) {
			this.brand_service = brand_service;
		}
		/**
		 * @return the coupons
		 */
		public List<Coupons> getCoupons() {
			return coupons;
		}
		/**
		 * @param coupons the coupons to set
		 */
		public void setCoupons(List<Coupons> coupons) {
			this.coupons = coupons;
		}
		/**
		 * @return the banner
		 */
		public List<BrandBanner> getBanner() {
			return banner;
		}
		/**
		 * @param banner the banner to set
		 */
		public void setBanner(List<BrandBanner> banner) {
			this.banner = banner;
		}
		/**
		 * @return the single
		 */
		public List<Product> getSingle() {
			return single;
		}
		/**
		 * @param single the single to set
		 */
		public void setSingle(List<Product> single) {
			this.single = single;
		}
		/**
		 * @return the allinone_url
		 */
		public String getAllinone_url() {
			return allinone_url;
		}
		/**
		 * @param allinone_url the allinone_url to set
		 */
		public void setAllinone_url(String allinone_url) {
			this.allinone_url = allinone_url;
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
		 * @return the brand_image
		 */
		public String getBrand_image() {
			return brand_image;
		}
		/**
		 * @param brand_image the brand_image to set
		 */
		public void setBrand_image(String brand_image) {
			this.brand_image = brand_image;
		}
		/**
		 * @return the brand_id
		 */
		public Long getBrand_id() {
			return brand_id;
		}
		/**
		 * @param brand_id the brand_id to set
		 */
		public void setBrand_id(Long brand_id) {
			this.brand_id = brand_id;
		}
		/**
		 * @return the collect_num
		 */
		public Long getCollect_num() {
			return collect_num;
		}
		/**
		 * @param collect_num the collect_num to set
		 */
		public void setCollect_num(Long collect_num) {
			this.collect_num = collect_num;
		}
		
}
