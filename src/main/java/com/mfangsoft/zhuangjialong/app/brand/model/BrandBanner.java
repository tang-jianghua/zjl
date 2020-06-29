package com.mfangsoft.zhuangjialong.app.brand.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：banner模型
* @author：Jianghua.Tang 
* @date：2016年5月27日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class BrandBanner {
     private String banner_url;
     private Integer brand_id;
     

	/**
	 * @return the brand_id
	 */
	public Integer getBrand_id() {
		return brand_id;
	}

	/**
	 * @param brand_id the brand_id to set
	 */
	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	/**
	 * @return the banner_url
	 */
	public String getBanner_url() {
		return banner_url;
	}

	/**
	 * @param banner_url the banner_url to set
	 */
	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}
     
} 
