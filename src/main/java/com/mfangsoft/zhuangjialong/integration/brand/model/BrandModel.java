package com.mfangsoft.zhuangjialong.integration.brand.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2017年1月3日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class BrandModel {

	private Integer main_num;//首页产品数
	
	private Integer product_num;//线上产品数
	
	private Integer banner_num;//banner数
	
	private Integer shop_num;//线上店铺数

	/**
	 * @return the main_num
	 */
	public Integer getMain_num() {
		return main_num;
	}

	/**
	 * @param main_num the main_num to set
	 */
	public void setMain_num(Integer main_num) {
		this.main_num = main_num;
	}

	/**
	 * @return the product_num
	 */
	public Integer getProduct_num() {
		return product_num;
	}

	/**
	 * @param product_num the product_num to set
	 */
	public void setProduct_num(Integer product_num) {
		this.product_num = product_num;
	}

	/**
	 * @return the banner_num
	 */
	public Integer getBanner_num() {
		return banner_num;
	}

	/**
	 * @param banner_num the banner_num to set
	 */
	public void setBanner_num(Integer banner_num) {
		this.banner_num = banner_num;
	}

	/**
	 * @return the shop_num
	 */
	public Integer getShop_num() {
		return shop_num;
	}

	/**
	 * @param shop_num the shop_num to set
	 */
	public void setShop_num(Integer shop_num) {
		this.shop_num = shop_num;
	}
	
	
}
