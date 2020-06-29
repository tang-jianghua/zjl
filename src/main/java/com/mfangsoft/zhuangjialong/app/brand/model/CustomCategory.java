package com.mfangsoft.zhuangjialong.app.brand.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：分类模型
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class CustomCategory {
	private String custom_category;
   private String custom_value;
/**
 * @return the custom_category
 */
public String getCustom_category() {
	return custom_category;
}
/**
 * @param custom_category the custom_category to set
 */
public void setCustom_category(String custom_category) {
	this.custom_category = custom_category;
}
/**
 * @return the custom_value
 */
public String getCustom_value() {
	return custom_value;
}
/**
 * @param custom_value the custom_value to set
 */
public void setCustom_value(String custom_value) {
	this.custom_value = custom_value;
}
/**
 * @param custom_category
 * @param custom_value
 */
public CustomCategory(String custom_category, String custom_value) {
	super();
	this.custom_category = custom_category;
	this.custom_value = custom_value;
}
   
}
