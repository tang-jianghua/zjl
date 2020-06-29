package com.mfangsoft.zhuangjialong.integration.order.model;

import java.util.Date;
import java.util.List;

public class Order {

	/**
	 * 产品型号
	 */
	private String product_model;
	/**
	 * 订单状态
	 */
	private Integer state;	
	/**
	 * 店面名称
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
	 * 评价状态
	 */
	private Integer  comment_state;	
	
	private Integer  region_id;
	
	/**
	 * 评价状态
	 */
	private String order_code;	
	/**
	 * 开始时间
	 */
	private Date start_time	;
	/**
	 * 结束时间
	 */
	private Date end_time;	
	
	

	public String getProduct_model() {
		return product_model;
	}

	public void setProduct_model(String product_model) {
		this.product_model = product_model;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getComment_state() {
		return comment_state;
	}

	public void setComment_state(Integer comment_state) {
		this.comment_state = comment_state;
	}

	public Integer getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Integer region_id) {
		this.region_id = region_id;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
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

	

	
}
