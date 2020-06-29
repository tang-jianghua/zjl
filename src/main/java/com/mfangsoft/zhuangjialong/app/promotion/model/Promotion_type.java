package com.mfangsoft.zhuangjialong.app.promotion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.Product;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月2日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class Promotion_type {
      private Integer id;
      private String promotion_type;
      private List<Product> products;
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
	 * @return the promotion_type
	 */
	public String getPromotion_type() {
		return promotion_type;
	}
	/**
	 * @param promotion_type the promotion_type to set
	 */
	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}
	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}
      
}
