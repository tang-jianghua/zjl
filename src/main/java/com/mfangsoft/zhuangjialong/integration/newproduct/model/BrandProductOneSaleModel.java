package com.mfangsoft.zhuangjialong.integration.newproduct.model;

public class BrandProductOneSaleModel extends BaseBrandProductEntity {

	private String imgurl;
	private String description;
	private String brand_name;
	private String hot_line;
	private String brand_imgurl;
	private String class_name;
	private String p_region_code;
	
	
	
	private BaseBrandProdSalesAttrEntity salesAttrEntity;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getHot_line() {
		return hot_line;
	}

	public void setHot_line(String hot_line) {
		this.hot_line = hot_line;
	}

	public String getBrand_imgurl() {
		return brand_imgurl;
	}

	public void setBrand_imgurl(String brand_imgurl) {
		this.brand_imgurl = brand_imgurl;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getP_region_code() {
		return p_region_code;
	}

	public void setP_region_code(String p_region_code) {
		this.p_region_code = p_region_code;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public BaseBrandProdSalesAttrEntity getSalesAttrEntity() {
		return salesAttrEntity;
	}

	public void setSalesAttrEntity(BaseBrandProdSalesAttrEntity salesAttrEntity) {
		this.salesAttrEntity = salesAttrEntity;
	}

}
