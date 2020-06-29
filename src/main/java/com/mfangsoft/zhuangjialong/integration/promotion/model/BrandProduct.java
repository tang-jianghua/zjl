package com.mfangsoft.zhuangjialong.integration.promotion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.Product;

@JsonInclude(value=Include.NON_NULL)
public class BrandProduct {

	private Long pid;
	private Long time_id;
	
	private Long brand_id;
	private String brand_name;
	private String principal;
	private String phone_num;
	private String imgurl;
	private String class_name;
	
	private List<Product> product;
	
	private List<Product> product_state0;//0未审核 1通过审核 2 审核未通过 3 申请下架 4 下架
	private List<Product> product_state1;
	private List<Product> product_state2;
	private List<Product> product_state3;
	private List<Product> product_state4;
	private List<Product> product_state5;
	
	public List<Product> getProduct_state0() {
		return product_state0;
	}
	public void setProduct_state0(List<Product> product_state0) {
		this.product_state0 = product_state0;
	}
	public List<Product> getProduct_state1() {
		return product_state1;
	}
	public void setProduct_state1(List<Product> product_state1) {
		this.product_state1 = product_state1;
	}
	public List<Product> getProduct_state2() {
		return product_state2;
	}
	public void setProduct_state2(List<Product> product_state2) {
		this.product_state2 = product_state2;
	}
	public List<Product> getProduct_state3() {
		return product_state3;
	}
	public void setProduct_state3(List<Product> product_state3) {
		this.product_state3 = product_state3;
	}
	
	public List<Product> getProduct_state4() {
		return product_state4;
	}
	public void setProduct_state4(List<Product> product_state4) {
		this.product_state4 = product_state4;
	}
	public List<Product> getProduct_state5() {
		return product_state5;
	}
	public void setProduct_state5(List<Product> product_state5) {
		this.product_state5 = product_state5;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getTime_id() {
		return time_id;
	}
	public void setTime_id(Long time_id) {
		this.time_id = time_id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	
	
	
}
