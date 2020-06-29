package com.mfangsoft.zhuangjialong.integration.comment.model;

import java.util.Date;

public class CommentQuery {
	
	/**
	 * 产品型号
	 */
	private String product_model;	
	/**
	 * 买家名称
	 */
	private String customer_name;	
	/**
	 * 店铺名称
	 */
	private String shop_name;	
	/**
	 * 品牌名称
	 */
	private String brand_name;		
	/**
	 * 城市合伙人
	 */
	private String city_partner;		
	/**
	 * 开始时间
	 */
	private Date start_time;		
	/**
	 * 结束时间
	 */
	private Date end_time;		
	/**
	 * 评价用户类型(0:买家，1:卖家)
	 */
	private Integer comment_object;	
	/**
	 * 评价状态
	 */
	private Integer comment_state;	
	/**
	 * 评价级别
	 */
	private Integer comment_level;	
	/**
	 * 是否有评价内容(0:是，1:无)
	 */
	private Integer has_content;
	
	public String getProduct_model() {
		return product_model;
	}
	public void setProduct_model(String product_model) {
		this.product_model = product_model;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getCity_partner() {
		return city_partner;
	}
	public void setCity_partner(String city_partner) {
		this.city_partner = city_partner;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Integer getComment_object() {
		return comment_object;
	}
	public void setComment_object(Integer comment_object) {
		this.comment_object = comment_object;
	}
	public Integer getComment_state() {
		return comment_state;
	}
	public void setComment_state(Integer comment_state) {
		this.comment_state = comment_state;
	}
	public Integer getComment_level() {
		return comment_level;
	}
	public void setComment_level(Integer comment_level) {
		this.comment_level = comment_level;
	}
	public Integer getHas_content() {
		return has_content;
	}
	public void setHas_content(Integer has_content) {
		this.has_content = has_content;
	}	


}
