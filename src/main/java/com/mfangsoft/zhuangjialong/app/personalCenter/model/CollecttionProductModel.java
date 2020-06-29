package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class CollecttionProductModel {
	
    private Long promotion_type_id;
    private Long product_id;
    private String imgurl;
    private String product_name;
    private Double price;
    private Double promotion_price;
    private String search_text;
    private Long customer_id;
    
	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public String getSearch_text() {
		return search_text;
	}
	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	public Long getPromotion_type_id() {
		return promotion_type_id;
	}
	public void setPromotion_type_id(Long promotion_type_id) {
		this.promotion_type_id = promotion_type_id;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPromotion_price() {
		return promotion_price;
	}
	public void setPromotion_price(Double promotion_price) {
		this.promotion_price = promotion_price;
	}
    
}
