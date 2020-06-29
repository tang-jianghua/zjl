package com.mfangsoft.zhuangjialong.app.buildClass.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.brand.model.Brand;

/**
* @description：品类模型
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class BuildClass {
  private Long class_id;
  private String class_name;
  private String class_image;
  private List<Brand> brands;
  private String region_code;
  
  

/**
 * @return the region_code
 */
public String getRegion_code() {
	return region_code;
}
/**
 * @param region_code the region_code to set
 */
public void setRegion_code(String region_code) {
	this.region_code = region_code;
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
 * @return the class_image
 */
public String getClass_image() {
	return class_image;
}
/**
 * @param class_image the class_image to set
 */
public void setClass_image(String class_image) {
	this.class_image = class_image;
}

}
