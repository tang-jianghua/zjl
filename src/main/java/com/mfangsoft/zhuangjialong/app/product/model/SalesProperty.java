package com.mfangsoft.zhuangjialong.app.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
* @description：销售属性模型
* @author：Jianghua.Tang 
* @date：2016年5月26日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class SalesProperty {
	//销售属性id
	private Long id;
	//颜色
      private String color;
      //库存
      private Long stock;
      //售价
      private Double price;
      //活动价
      private Double new_price;
      //颜色组图片
      private String color_img;
      //型号
      private String model;
      //颜色型号
      private String color_model;
      //产品id
      private Long product_id;
      
    //销售属性id
  	private Long property_id;
      
  	
  	
	/**
	 * @return the property_id
	 */
	public Long getProperty_id() {
		return property_id;
	}
	/**
	 * @param property_id the property_id to set
	 */
	public void setProperty_id(Long property_id) {
		this.property_id = property_id;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the color_model
	 */
	public String getColor_model() {
		return color_model;
	}
	/**
	 * @param color_model the color_model to set
	 */
	public void setColor_model(String color_model) {
		this.color_model = color_model;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	public Double getNew_price() {
		return new_price;
	}
	public void setNew_price(Double new_price) {
		this.new_price = new_price;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * @return the stock
	 */
	public Long getStock() {
		return stock;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(Long stock) {
		this.stock = stock;
	}
	/**
	 * @return the product_id
	 */
	public Long getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the color_img
	 */
	public String getColor_img() {
		return color_img;
	}
	/**
	 * @param color_img the color_img to set
	 */
	public void setColor_img(String color_img) {
		this.color_img = color_img;
	}


	
}
