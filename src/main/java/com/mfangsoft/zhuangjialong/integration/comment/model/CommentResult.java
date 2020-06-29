package com.mfangsoft.zhuangjialong.integration.comment.model;

import java.util.Date;
import java.util.List;

public class CommentResult {

	/**
	 * 评价级别
	 */
	private Integer comment_level;	
	/**
	 * 评价内容
	 */
	private String comment_content;	
	/**
	 * 评价时间
	 */
	private  Date  comment_time;	
	/**
	 * 用户名称
	 */
	private  String customer_name;	
	/**
	 * 用户级别
	 */
	private Integer customer_level;	
	/**
	 * 产品名称
	 */
	private String product_name;		
	/**
	 * 产品价格
	 */
	private Double product_price;
	/**;
	 * 城市合伙人
	 */
	private String city_partner;		
	/**
	 * 品牌名称
	 */
	private String brand_name;		
	/**
	 * 店铺名称
	 */
	private String shop_name;		
	/**
	 * 
	 */
	private List<String> img;
	public Integer getComment_level() {
		return comment_level;
	}
	public void setComment_level(Integer comment_level) {
		this.comment_level = comment_level;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public Date getComment_time() {
		return comment_time;
	}
	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Integer getCustomer_level() {
		return customer_level;
	}
	public void setCustomer_level(Integer customer_level) {
		this.customer_level = customer_level;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Double product_price) {
		this.product_price = product_price;
	}
	public String getCity_partner() {
		return city_partner;
	}
	public void setCity_partner(String city_partner) {
		this.city_partner = city_partner;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public List<String> getImg() {
		return img;
	}
	public void setImg(List<String> img) {
		this.img = img;
	}
	
	

}
