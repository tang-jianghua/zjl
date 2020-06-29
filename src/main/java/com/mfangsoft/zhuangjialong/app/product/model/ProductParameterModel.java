package com.mfangsoft.zhuangjialong.app.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月27日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ProductParameterModel {
    
         private String description;
         private List<String> case_imgs;
         private List<SelectPropertiesModel> params;
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		/**
		 * @return the case_imgs
		 */
		public List<String> getCase_imgs() {
			return case_imgs;
		}
		/**
		 * @param case_imgs the case_imgs to set
		 */
		public void setCase_imgs(List<String> case_imgs) {
			this.case_imgs = case_imgs;
		}
		/**
		 * @return the params
		 */
		public List<SelectPropertiesModel> getParams() {
			return params;
		}
		/**
		 * @param params the params to set
		 */
		public void setParams(List<SelectPropertiesModel> params) {
			this.params = params;
		}
         
         
      
         
       
         
}
