package com.mfangsoft.zhuangjialong.app.product.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.common.model.Page;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月14日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ClassProductModel {
        private String class_name;
        private Page<Map<String, Object>> resultPage;
        //private Page<ProductListModel> resultPage;
        
        
	/*	*//**
		 * @return the resultPage
		 *//*
		public Page<ProductListModel> getResultPage() {
			return resultPage;
		}
		*//**
		 * @param resultPage the resultPage to set
		 *//*
		public void setResultPage(Page<ProductListModel> resultPage) {
			this.resultPage = resultPage;
		}*/
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
		 * @return the resultPage
		 */
		public Page<Map<String, Object>> getResultPage() {
			return resultPage;
		}
		/**
		 * @param resultPage the resultPage to set
		 */
		public void setResultPage(Page<Map<String, Object>> resultPage) {
			this.resultPage = resultPage;
		}
		
}
