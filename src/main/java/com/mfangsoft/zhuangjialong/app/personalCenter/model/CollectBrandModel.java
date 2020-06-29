package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月17日
* 
*/

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月17日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class CollectBrandModel {
        private Integer id;
        private Long brand_id;
        private String imgurl;
        private String brand_name;

        /**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
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
		 * @return the imgurl
		 */
		public String getImgurl() {
			return imgurl;
		}
		/**
		 * @param imgurl the imgurl to set
		 */
		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
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
        
}
