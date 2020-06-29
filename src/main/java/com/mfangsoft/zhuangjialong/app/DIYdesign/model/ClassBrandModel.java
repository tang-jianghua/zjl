package com.mfangsoft.zhuangjialong.app.DIYdesign.model;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.brand.model.Brand;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月21日
* 
*/

public class ClassBrandModel {
         private Long class_id;
         private String class_name;
         List<Brand> brands;
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
		 * @return the class_name
		 */
		public String getClass_name() {
			return class_name;
		}
		/**
		 * @param class_name the class_name to set
		 */
		public void setClass_name(String class_name) {
			this.class_name = class_name;
		}
		/**
		 * @return the brands
		 */
		public List<Brand> getBrands() {
			return brands;
		}
		/**
		 * @param brands the brands to set
		 */
		public void setBrands(List<Brand> brands) {
			this.brands = brands;
		}
         
         
         
         
}
