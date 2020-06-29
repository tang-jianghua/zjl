package com.mfangsoft.zhuangjialong.app.brand.model;

import java.util.List;

import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月6日
* 
*/

public class BrandEnModel {
       private String first_letter;
       private List<BrandModel> brands;
	/**
	 * @return the first_letter
	 */
	public String getFirst_letter() {
		return first_letter;
	}
	/**
	 * @param first_letter the first_letter to set
	 */
	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}
	/**
	 * @return the brands
	 */
	public List<BrandModel> getBrands() {
		return brands;
	}
	/**
	 * @param brands the brands to set
	 */
	public void setBrands(List<BrandModel> brands) {
		this.brands = brands;
	}

       
       
}
